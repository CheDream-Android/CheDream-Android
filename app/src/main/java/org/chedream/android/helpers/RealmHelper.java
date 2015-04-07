package org.chedream.android.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.chedream.android.database.RealmDream;
import org.chedream.android.database.RealmPicture;
import org.chedream.android.database.RealmUser;
import org.chedream.android.model.Dream;
import org.chedream.android.model.EquipmentContribution;
import org.chedream.android.model.EquipmentResource;
import org.chedream.android.model.FinancialContribution;
import org.chedream.android.model.FinancialResource;
import org.chedream.android.model.Picture;
import org.chedream.android.model.User;
import org.chedream.android.model.WorkResource;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Dante Allteran on 3/16/2015.
 */
public class RealmHelper {
    private static final String TAG = RealmHelper.class.getSimpleName();

    /**
     * In DB user wont see any contribution or resources
     */
    public void addDreamToDatabase(Realm realm, Dream currentDream, Context activity) {
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

        /**Third - add all pictures to database (mediaPoster, mediaCompletedPictures, mediaPictures)*/
        RealmPicture mediaPoster = realm.createObject(RealmPicture.class);
        mediaPoster.setId(realmMediaPoster.getId());
        mediaPoster.setProviderReference(realmMediaPoster.getProviderReference());
        mediaPoster.setHeight(realmMediaPoster.getHeight());
        mediaPoster.setName(realmMediaPoster.getName());
        mediaPoster.setWidth(realmMediaPoster.getWidth());

        RealmList<RealmPicture> mediaPictures = new RealmList<>();
        for (int i = 0; i < realmMediaPictures.size(); i++) {
            RealmPicture picture = realm.createObject(RealmPicture.class);
            picture.setId(realmMediaPictures.get(i).getId());
            picture.setName(realmMediaPictures.get(i).getName());
            picture.setProviderReference(realmMediaPictures.get(i).getProviderReference());
            picture.setWidth(realmMediaPictures.get(i).getWidth());
            picture.setHeight(realmMediaPictures.get(i).getHeight());
            mediaPictures.add(picture);
        }

        RealmList<RealmPicture> mediaCompletedPictures = new RealmList<>();
        for (int i = 0; i < realmMediaCompletedPictures.size(); i++) {
            RealmPicture picture = realm.createObject(RealmPicture.class);
            picture.setId(mediaCompletedPictures.get(i).getId());
            picture.setName(mediaCompletedPictures.get(i).getName());
            picture.setProviderReference(mediaCompletedPictures.get(i).getProviderReference());
            picture.setWidth(mediaCompletedPictures.get(i).getWidth());
            picture.setHeight(mediaCompletedPictures.get(i).getHeight());
            mediaCompletedPictures.add(picture);
        }

        /**Fourth - add all quantities to DB*/
        int finResQuantity = 0;
        for (FinancialResource resource : currentDream.getDreamFinancialResources()) {
            finResQuantity += resource.getQuantity();
        }

        int finContQuantity = 0;
        for (FinancialContribution contribution : currentDream.getDreamFinancialContributions()) {
            finContQuantity += contribution.getQuantity();
        }

        int workResQuantity = 0;
        for (WorkResource resource : currentDream.getDreamWorkResources()) {
            workResQuantity += resource.getQuantity();
        }

        int workContQuantity = 0;
        for (FinancialContribution contribution : currentDream.getDreamFinancialContributions()) {
            workContQuantity += contribution.getQuantity();
        }

        int equipResQuantity = 0;
        for (EquipmentResource resource : currentDream.getDreamEquipmentResources()) {
            equipResQuantity += resource.getQuantity();
        }

        int equipContQuantity = 0;
        for (EquipmentContribution contribution : currentDream.getDreamEquipmentContributions()) {
            equipContQuantity += contribution.getQuantity();
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
        dream.setAuthor(author);
        dream.setCurrentStatus(realmDream.getCurrentStatus());
        dream.setFinResQuantity(finResQuantity);
        dream.setFinContribQuantity(finContQuantity);
        dream.setEquipResQuantity(equipResQuantity);
        dream.setEquipContribQuantity(equipContQuantity);
        dream.setWorkResQuantity(workResQuantity);
        dream.setWorkContribQuantity(workContQuantity);

        dream.setMediaPoster(mediaPoster);
        dream.setMediaPictures(mediaPictures);
        dream.setMediaCompletedPictures(mediaCompletedPictures);
        Log.i(TAG, "Dream \"" + dream.getTitle() + "\" was added to realm database");

        realm.commitTransaction();
        Toast.makeText(activity, "Dream was added to DB", Toast.LENGTH_LONG).show();
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

    public boolean isDreamInDatabase(Realm realm, Dream incomingDream) {
        List<RealmDream> result = realm.where(RealmDream.class)
                .equalTo("id", incomingDream.getId())
                .findAll();
        return result.size() != 0;
    }

    public void deleteAllDreamsFromDatabase(Realm realm, Context context) {
        realm.close();
        Realm.deleteRealmFile(context);
    }

    public void deleteDreamFromDatabase(Realm realm, Dream incomingDream, Context activity) {
        realm.beginTransaction();
        List<RealmDream> result = realm
                .where(RealmDream.class)
                .equalTo("id", incomingDream.getId())
                .findAll();
        result.remove(0);
        realm.commitTransaction();
        Toast.makeText(activity, "Dream was deleted from DB", Toast.LENGTH_SHORT).show();
    }

    public int getFinResQuantity(Realm realm, int position) {
        List<RealmDream> dreamsFromDb = realm
                .where(RealmDream.class)
                .findAll();

        return dreamsFromDb.get(position).getFinResQuantity();
    }

    public int getFinContQuantity(Realm realm, int position) {
        List<RealmDream> dreamsFromDb = realm
                .where(RealmDream.class)
                .findAll();

        return dreamsFromDb.get(position).getFinContribQuantity();
    }

    public int getEquipResQuantity(Realm realm, int position) {
        List<RealmDream> dreamsFromDb = realm
                .where(RealmDream.class)
                .findAll();

        return dreamsFromDb.get(position).getEquipResQuantity();
    }

    public int getEquipContQuantity(Realm realm, int position) {
        List<RealmDream> dreamsFromDb = realm
                .where(RealmDream.class)
                .findAll();

        return dreamsFromDb.get(position).getEquipContribQuantity();
    }

    public int getWorkResQuantity(Realm realm, int position) {
        List<RealmDream> dreamsFromDb = realm
                .where(RealmDream.class)
                .findAll();

        return dreamsFromDb.get(position).getWorkResQuantity();
    }

    public int getWorkContQuantity(Realm realm, int position) {
        List<RealmDream> dreamsFromDb = realm
                .where(RealmDream.class)
                .findAll();

        return dreamsFromDb.get(position).getWorkContribQuantity();
    }


}