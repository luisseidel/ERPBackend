package com.seidelsoft.ERPBackend.rabbitMQ;

import java.io.Serializable;

public record TaskMessage(String taskType, String payload) implements Serializable {
}
