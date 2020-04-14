## Network firewall polices
 * `Deny` - (or `DROP`) drop the traffic without any response, client will be wait for connection timeout.
 If connection timeout is huge it can affect client. 
 * `Reject` - the message about connection rejection will be sent to the requester.
 
 This actually means that if U use `Reject` everyone can scan network and find U'r machine connection.
 Probably it's better to use it only for private network communications.  
 
 ---
 [1](https://kb.zyxel.com/KB/searchArticle!gwsViewDetail.action?articleOid=012926&lang=EN)