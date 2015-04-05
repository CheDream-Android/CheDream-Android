package org.chedream.android.helpers;

import android.content.Context;
import android.util.Log;

import org.chedream.android.database.RealmDream;
import org.chedream.android.database.RealmPicture;
import org.chedream.android.database.RealmUser;
import org.chedream.android.model.Dream;
import org.chedream.android.model.Picture;
import org.chedream.android.model.User;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Dante Allteran on 3/16/2015.
 */
public class RealmHelper {
    private static final String TAG = RealmHelper.class.getSimpleName();

    /**
     * In DB user wont see any contribution or resources
     */
    public void addDreamToDatabase(Realm realm, Dream currentDream) {
        /**Prepare to cast field 'author' from Dream to  RealmDream*/
        RealmUser currentAuthor = new RealmUser(currentDream.getAuthor());

        /**Do the same trick with field 'usersWhoFavorite*/
        RealmList<RealmUser> currentUsersWhoFav = new RealmList<>();
        for (User user : currentDream.getUsersWhoFavorites()) {
            currentUsersWhoFav.add(new RealmUser(user));
        }

        /**Prepare to cast field 'mediaPoster' from Picture to RealmPicture*/
        RealmPicture realmMediaPoster = new RealmPicture(currentDream.getMediaPoster());

        /**Do the same with fields 'mediaPicture' and 'mediaCompletedPictures'*/
        RealmList<RealmPicture> realmMediaPictures = new RealmList<>();
        for (Picture picture : currentDream.getMediaPictures()) {
            realmMediaPictures.add(new RealmPicture(picture));
        }
        RealmList<RealmPicture> realmMediaCompletedPictures = new RealmList<>();
        for (Picture picture : currentDream.getMediaCompletedPictures()) {
            realmMediaCompletedPictures.add(new RealmPicture(picture));
        }

        /**And go on the same with dream*/
        RealmDream realmDream = new RealmDream(currentDream, currentAuthor, currentUsersWhoFav,
                realmMediaPoster, realmMediaPictures, realmMediaCompletedPictures);

        realm.beginTransaction();

        /**First - add user component to DB */
        RealmUser author = realm.createObject(RealmUser.class);
        author.setId(currentAuthor.getId());
        author.setUsername(currentAuthor.getUsername());
        author.setFirstName(currentAuthor.getFirstName());
        author.setLastName(currentAuthor.getLastName());
        author.setAvatar(currentAuthor.getAvatar());

        /**Second - add users, who favorites to DB*/
        RealmList<RealmUser> usersWhoFavorite = new RealmList<>();
        for (int i = 0; i < currentUsersWhoFav.size(); i++) {
            RealmUser rUser = realm.createObject(RealmUser.class);
            rUser.setId(currentUsersWhoFav.get(i).getId());
            rUser.setUsername(currentUsersWhoFav.get(i).getUsername());
            rUser.setFirstName(currentUsersWhoFav.get(i).getFirstName());
            rUser.setLastName(currentUsersWhoFav.get(i).getLastName());
            rUser.setAvatar(currentUsersWhoFav.get(i).getAvatar());
            usersWhoFavorite.add(rUser);
        }

        /**And add current dream to DB*/
        RealmDream dream = realm.createObject(RealmDream.class);
        dream.setId(realmDream.getId());
        dream.setTitle(realmDream.getTitle());
        dream.setDescription(realmDream.getDescription());
        dream.setPhone(realmDream.getPhone());
        dream.setSlug(realmDream.getSlug());
        dream.setCreatedAt(realmDream.getCreatedAt());
        dream.setUpdatedAt(realmDream.getUpdatedAt());
        dream.setUsersWhoFavorites(usersWhoFavorite);
        dream.setAuthor(currentAuthor);
        dream.setCurrentStatus(realmDream.getCurrentStatus());
        Log.i(TAG, "Dream \"" + dream.getTitle() + "\" was added to realm database");

        realm.commitTransaction();
    }

    /**
     * This method should do the reverse action to @method addDreamToDatabase
     * First - get all RealmDreams from DB
     * Second - get author from RealmDream and set it to Dream
     * Third - get ArrayList<RealmUser> usersWhoFavorite and set it to Dream
     * Step by step we cast that Realm-models into native models
     */
    public List<Dream> getDreamsFromDatabase(Realm realm) {
        List<RealmDream> dreamsFromDb = realm
                .where(RealmDream.class)
                .findAll();

        ArrayList<Dream> nativeDream = new ArrayList<>();
        for (int i = 0; i < dreamsFromDb.size(); i++) {
            Dream dream = new Dream();
            dream.setId(dreamsFromDb.get(i).getId());
            dream.setTitle(dreamsFromDb.get(i).getTitle());
            dream.setDescription(dreamsFromDb.get(i).getDescription());
            dream.setPhone(dreamsFromDb.get(i).getPhone());
            dream.setSlug(dreamsFromDb.get(i).getSlug());
            dream.setCreatedAt(dreamsFromDb.get(i).getCreatedAt());
            dream.setUpdatedAt(dreamsFromDb.get(i).getUpdatedAt());
            dream.setDeletedAt(dreamsFromDb.get(i).getDeletedAt());
            dream.setCurrentStatus(dreamsFromDb.get(i).getCurrentStatus());

            ArrayList<User> usersWhoFavorite = new ArrayList<>();
            for (int j = 0; j < dreamsFromDb.get(i).getUsersWhoFavorites().size(); j++) {
                User userWhoFavorite = new User(dreamsFromDb.get(i).getUsersWhoFavorites().get(j));
                usersWhoFavorite.add(userWhoFavorite);
            }
            dream.setUsersWhoFavorites(usersWhoFavorite);

            User author = new User(dreamsFromDb.get(i).getAuthor());
            dream.setAuthor(author);

            ArrayList<Picture> mediaPictures = new ArrayList<>();
            for (int j = 0; j < dreamsFromDb.get(i).getMediaPictures().size(); j++) {
                Picture mediaPicture = new Picture(dreamsFromDb.get(i).getMediaPictures().get(j));
                mediaPictures.add(mediaPicture);
            }
            dream.setMediaPictures(mediaPictures);

            ArrayList<Picture> mediaCompletedPictures = new ArrayList<>();
            for (int j = 0; j < dreamsFromDb.get(i).getMediaCompletedPictures().size(); j++) {
                Picture mediaCompletedPicture = new Picture(dreamsFromDb.get(i).getMediaCompletedPictures().get(j));
                mediaCompletedPictures.add(mediaCompletedPicture);
            }
            dream.setMediaCompletedPictures(mediaCompletedPictures);

            Picture mediaPoster = new Picture(dreamsFromDb.get(i).getMediaPoster());
            dream.setMediaPoster(mediaPoster);

            nativeDream.add(dream);
        }
        return nativeDream;
    }

    public boolean isDrealmInDatabase(Realm realm, Dream incomingDream) {
        List<RealmDream> result = realm.where(RealmDream.class)
                .equalTo("id", incomingDream.getId())
                .findAll();
        return result.size() != 0;
    }

    public void deleteAllDreamsFromDatabase(Realm realm, Context context) {
        realm.close();
        Realm.deleteRealmFile(context);
    }
}