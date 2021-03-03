package com.app.helper.tools;


import com.app.helper.to.WsMsg;

import okhttp3.WebSocket;

interface IWsManager {
 
  WebSocket getWebSocket();
 
  void startConnect();
 
  void stopConnect();
 
  boolean isWsConnected();
 
  int getCurrentStatus();
 
  void setCurrentStatus(int currentStatus);
 
  boolean sendMessage(WsMsg msg);
}