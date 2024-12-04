package ru.astradev.toy_store.api.job;

import ru.astradev.toy_store.core.model.Card;

import java.time.LocalDate;

public class CardAuth {

    public static boolean check(Card bankCard){

        if (bankCard.getBankCard() != null && bankCard.getCvv() != null
         && bankCard.getMonthYear() != null && bankCard.getOwner() != null &&
            bankCard.getBankCard().length() == 19 && bankCard.getCvv().length() == 3 &&
            bankCard.getMonthYear().length() == 5){

            String[] monthYear = bankCard.getMonthYear().split("/");
            if (Integer.parseInt(monthYear[0]) < 13 &&
                    (Integer.parseInt("20"+monthYear[1]) > LocalDate.now().getYear() ||
                            Integer.parseInt("20"+monthYear[1]) == LocalDate.now().getYear() &
                                    Integer.parseInt(monthYear[0]) >  LocalDate.now().getMonth().getValue())){
                return (bankCard.getOwner().matches("[A-Z]+ [A-Z]+"));
            }
        }
        return false;
    }
}
