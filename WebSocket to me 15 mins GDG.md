# WebSocket to Me!

## Chris Guzman

---

# What are WebSockets?

- bi-directional
- before you had to poll the server
- or use webhooks
- now the server can push to you
- mainly used in browsers
- keeps a constant connection open

---

## Why should browsers have all the fun?

Android gif

---

# Sockets vs WebSockets

WebSockets are Sockets, just with extra protocols:
- Connection scheme
- Security scheme
- Steps to follow to connect

WebSockets start with an HTTP request from client to server.
Then the connection "upgrades" to the WebSocket protocol.

---

# WebSockets vs WebRTC

- WebRTC is peer to peer, not client/server
- WebRTC is better suited for audio/video
- WebSockets are better for text or transferring of bytes

---

# Who uses WebSockets?

- [Trello](https://www.reddit.com/r/androiddev/comments/32jr2y/how_do_applications_like_trello_work/)
- [Slack](https://api.slack.com/rtm)

---

# What is okhttp

---

# lifecycles of websocket
- Connecting: the initial state of each web socket. Messages may be enqueued but they won't be transmitted until the web socket is open.
- Open: the web socket has been accepted by the remote peer and is fully operational. Messages in either direction are enqueued for immediate transmission.
- Closing: one of the peers on the web socket has initiated a graceful shutdown. The web socket will continue to transmit already-enqueued messages but will refuse to enqueue new ones.
- Closed: the web socket has transmitted all of its messages and has received all messages from the peer.
- Canceled: the web socket connection failed. Messages that were successfully enqueued by either peer may not have been transmitted to the other.


State progression is independent for each peer.

Arriving at a gracefully-closed state indicates that a peer has sent all of its outgoing messages and received all of its incoming messages. But it does not guarantee that the other peer will successfully receive all of its incoming messages.

Just cuz you closed gracefully, doesn't mean the other side did.


---

# How does OkHttp do WebSockets?

- Looks like you can use builder pattern or constructors

---

# Example
gif/video of the grinch echo https://www.youtube.com/watch?v=ZU0wyJxAZ6c

---

#Live code?

---

# What other libraries can I use?

See this list:
http://stackoverflow.com/questions/30547517/which-websocket-library-to-use-in-android-app

---

# Drawbacks of WebSockets
- Not supported by HTTP/2
  - https://www.infoq.com/articles/websocket-and-http2-coexist
  - https://daniel.haxx.se/blog/2016/06/15/no-websockets-over-http2/
- Can drain battery if connection is constantly open
- Could be negatively affected by Doze

---

# What's next?

- WebRTC
- WTF is a streaming API?
It just keeps the connection alive. Seems dumb to me.

---

# Cite sources:

- http://www.ssaurel.com/blog/learn-to-use-websockets-on-android-with-okhttp/?utm_source=androiddevdigest
