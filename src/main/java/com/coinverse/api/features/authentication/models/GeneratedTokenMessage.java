package com.coinverse.api.features.authentication.models;

import com.coinverse.api.common.models.StringToken;
import com.coinverse.api.features.messaging.models.Message;

@FunctionalInterface
public interface GeneratedTokenMessage {
    Message getMessage(StringToken token);
}
