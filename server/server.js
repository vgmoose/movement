var app = require('express');
var server = require('http').Server(app);
var io = require('socket.io')(server);

server.listen(3001);

clients = [];

function addClient(client)
{
    clients.push(client);
    return clients.length;
}

// handle incoming connections from clients
io.sockets.on('connection', function(socket) {
              // once a client has connected, we expect to get a ping from them saying what room they want to join
              var id = addClient(socket);
              console.log("New client connected, assigned " + id);
              socket.emit('id_assign', {'id' : id});
              
              socket.on('get_move', function(data) {
                        console.log("sending " + data + " to all clients");
                        socket.broadcast.emit('get_move', data);
                        });
              
              socket.on('add_player', function(data) {
                        console.log(data);
                        });
              });

//// now, it's easy to send a message to just the clients in a given room
//room = "abc123";
//io.sockets.in(room).emit('message', 'what is going on, party people?');
//
//// this message will NOT go to the client defined above
//io.sockets.in('foobar').emit('message', 'anyone in this room yet?');
