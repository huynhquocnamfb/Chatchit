package vn.edu.nlu.web.chat.enums;

public enum MessageStatus {
    SENT,
    DELIVERED,
    READ,
    FAILED, // If message delivery failed
    FORWARDED
}