package org.matmech.tests;

import java.util.HashMap;

public interface TestsInterface {
    String getQuestion(String tag, String group, String mode);
    void clearTestQuestions(String tag);
}
