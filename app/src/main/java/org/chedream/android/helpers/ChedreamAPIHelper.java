package org.chedream.android.helpers;

import org.chedream.android.model.Dream;
import org.chedream.android.model.EquipmentContribution;
import org.chedream.android.model.EquipmentResource;
import org.chedream.android.model.FinancialContribution;
import org.chedream.android.model.FinancialResource;
import org.chedream.android.model.WorkResource;

import java.util.ArrayList;

public class ChedreamAPIHelper {

    public static int getOverallFinResQuantity(Dream dream) {
        int quantity = 0;

        for(FinancialResource resource: dream.getDreamFinancialResources()) {
            quantity += resource.getQuantity();
        }
        return quantity;
    }

    public static int getOverallEquipResQuantity(Dream dream) {
        int quantity = 0;

        for(EquipmentResource resource: dream.getDreamEquipmentResources()) {
            quantity += resource.getQuantity();
        }
        return quantity;
    }

    public static int getOverallWorkResQuantity(Dream dream) {
        int quantity = 0;

        for(WorkResource resource: dream.getDreamWorkResources()) {
            quantity += resource.getQuantity();
        }
        return quantity;
    }

    public static int getCurrentFinContribQuantity(Dream dream) {
        int quantity = 0;

        for(FinancialContribution contribution: dream.getDreamFinancialContributions()) {
            quantity += contribution.getQuantity();
        }
        return quantity;
    }

    public static int getCurrentEquipContribQuantity(Dream dream) {
        int quantity = 0;

        for(EquipmentContribution contribution: dream.getDreamEquipmentContributions()) {
            quantity += contribution.getQuantity();
        }
        return quantity;
    }

    public static int getCurrentWorkContribQuantity(Dream dream) {
        int quantity = 0;

        for(FinancialContribution contribution: dream.getDreamFinancialContributions()) {
            quantity += contribution.getQuantity();
        }
        return quantity;
    }
}
