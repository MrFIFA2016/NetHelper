package com.app.networkhelper.tools;

import com.app.networkhelper.to.WsMsg;
import okhttp3.WebSocket;
import okio.ByteString;

interface IWsManager {
 
  WebSocket getWebSocket();
 
  void startConnect();
 
  void stopConnect();
 
  boolean isWsConnected();
 
  int getCurrentStatus();
 
  void setCurrentStatus(int currentStatus);
 
//  boolean sendMessage(String msg);
//
//  boolean sendMessage(ByteString byteString);

  boolean sendMessage(WsMsg msg);
}