package ru.astradev.toy_store.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Card {
    private String bankCard;
    private String monthYear;
    private String cvv;
    private String owner;
}
