package org.chedream.android.database;

import android.util.Log;

import org.chedream.android.model.Dream;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Dante Allteran on 3/16/2015.
 */
public class RealmHelper {
    private static final String TAG = RealmHelper.class.getSimpleName();

    public void addTestDreamToDatabase(Realm realm, final org.chedream.android.model.test.Dream currentDream) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                org.chedream.android.model.test.Dream dream = realm.createObject(org.chedream.android.model.test.Dream.class);
                dream.setTitle(currentDream.getTitle());
                dream.setDescription(currentDream.getDescription());
                dream.setImage(currentDream.getImage());
                dream.setLikes(currentDream.getLikes());
                dream.setMoneyMax(currentDream.getMoneyMax());
                dream.setMoneyCurrent(currentDream.getMoneyCurrent());
                dream.setToolsMax(currentDream.getToolsMax());
                dream.setToolsCurrent(currentDream.getToolsCurrent());
                dream.setPeopleMax(currentDream.getPeopleMax());
                dream.setPeopleCurrent(currentDream.getPeopleCurrent());
                Log.i(TAG, "Dream \"" + dream.getTitle() + "\" was added to realm database");
            }
        });
    }

    public RealmResults<org.chedream.android.model.test.Dream> getAllTestDreams(Realm realm) {
        RealmResults<org.chedream.android.model.test.Dream> dreams = realm
                .where(org.chedream.android.model.test.Dream.class)
                .findAll();
        Log.i(TAG, dreams.first().getTitle() + " has been taken from DB");
        return dreams;
    }
//end this method
    public void deleteAllTestDreams(Realm realm, List<org.chedream.android.model.test.Dream> dreams) {
        RealmResults<org.chedream.android.model.test.Dream> dbDreams = getAllTestDreams(realm);

        realm.beginTransaction();

    }


    public void addDreamToDatabase(Realm realm, Dream currentDream) {
//        realm.beginTransaction();
//
//        Dream dream = realm.createObject(Dream.class);
//        dream.setId(currentDream.getId());
//        dream.setTitle(currentDream.getTitle());
//        dream.setDescription(currentDream.getDescription());
//        dream.setRejectedDescription(currentDream.getRejectedDescription());
//        dream.setImplementedDescription(currentDream.getImplementedDescription());
//        dream.setCompletedDescription(currentDream.getCompletedDescription());
//        dream.setPhone(currentDream.getPhone());
//        dream.setSlug(currentDream.getSlug());
//        dream.setCreatedAt(currentDream.getCreatedAt());
//        dream.setUpdatedAt(currentDream.getUpdatedAt());
//        dream.setExpiredDate(currentDream.getExpiredDate());
//        dream.setFinancialCompleted(currentDream.getFinancialCompleted());
//        dream.setWorkCompleted(currentDream.getWorkCompleted());
//        dream.setEquipmentCompleted(currentDream.getEquipmentCompleted());
//        dream.setUsersWhoFavorites(currentDream.getUsersWhoFavorites());
//        dream.setAuthor(currentDream.getAuthor());
//        dream.setCurrentStatus(currentDream.getCurrentStatus());
//        dream.setDreamFinancialResources(currentDream.getDreamFinancialResources());
//        dream.setDreamEquipmentResources(currentDream.getDreamEquipmentResources());
//        dream.setDreamWorkResources(currentDream.getDreamWorkResources());
//        dream.setDreamFinancialContributions(currentDream.getDreamFinancialContributions());
//
//        realm.commitTransaction();
        Log.i(TAG, "Dream \"" + "\" was added to realm database");
    }
}