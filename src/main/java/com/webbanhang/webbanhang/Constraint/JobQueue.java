package com.webbanhang.webbanhang.Constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobQueue {
    public static final String QUEUE_SEND_EMAIL = "rabbit-queue-send-email";
    public static final String QUEUE_SEND_EMAIL_RESET_PASS = "rabbit-queue-reset-pass";
    public static final List<String> QUEUE_NAMES = new ArrayList<>(List.of(QUEUE_SEND_EMAIL_RESET_PASS, QUEUE_SEND_EMAIL));
}

