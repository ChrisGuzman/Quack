package com.chris_guzman.learn_to_use_websockets;

import okio.ByteString;

/**
 * Created by chrisguzman on 4/4/17.
 */

interface ShowInActivityHandler {
    void showMessage(String text);
    void showImage(ByteString bytes);
}
