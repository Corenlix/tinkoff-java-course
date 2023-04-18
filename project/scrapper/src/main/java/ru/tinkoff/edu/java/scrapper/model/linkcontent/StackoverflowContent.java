package ru.tinkoff.edu.java.scrapper.model.linkcontent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public final class StackoverflowContent implements LinkContent {
    private boolean isAnswered;
    private int answerCount;
}
