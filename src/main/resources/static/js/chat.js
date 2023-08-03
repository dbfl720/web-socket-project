const url = 'http://localhost:8080';
let stompClient;
let selectedUser;


// chat와 연결하는 메소드
function connectToChat(userName) {
    console.log("connecting to chat...")
    let socket = new SockJS(url + '/chat');   // /chat은 MessageController - @MessageMapping("/chat/{to}") 에서 옴.
    stompClient = Stomp.over(socket);   // stomp는 http에 모델링된 frame 기반의 단순한 메세징 프로토콜이다. This method expects an object that conforms to the WebSocket definition.
    stopClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) { // topic은 MessageController - simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
               let data = JSON.parse(response.body);   //  stomp은 단순한 메시지 프로토콜이기 때문에 , JSON이 필요한 것임. // JSON.parse() 메서드는 JSON 문자열의 구문을 분석하고, 그 결과에서 JavaScript 값이나 객체를 생성합니다.
               console.log(data);
        });
    });
}

// 메시지를 서버에 보내는 메소드
function sendMsg(from, text) {
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from,  // model - MessageModel 객체명과 일치해야함.
        message: text    // model - MessageModel 객체명과 일치해야함.
    }));
}


function registration() {
    let userName = document.getElementById("userName").value;
    $.get(url + "/registration/" + userName, function (response) {  // UserController -   @GetMapping("/registration/{userName}") 여기에서 옴.
        connectToChat(userName);  // 성공하면 connect하기.
    }).fail(function (error) {
        if(error.status === 400) {
        alert("Login is already busy!")
        }
    })
}


function selectUser(userName) {
    console.log("selecting users: " + userName);
    selectedUser = userName;
    $('#selectedUserId').html('');
    $('#selectedUserId').append('Chat with ' + userName);
}


function fetchAll() {

    $.get(url + "/fetchAllUsers", function (response) {  // UserController -   @GetMapping("/fetchAllUsers") 여기에서 옴.
           let users = response;
           let usersTemplateHTML = "";
           for(let i = 0; i < users.length; i++) {
           usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i] + '\')"><li class="clearfix">\n' +
                                                                     '                <img src="https://rtfm.co.ua/wp-content/plugins/all-in-one-seo-pack/images/default-user-image.png" width="55px" height="55px" alt="avatar" />\n' +
                                                                     '                <div class="about">\n' +
                                                                     '                    <div id="userNameAppender_' + users[i] + '" class="name">' + users[i] + '</div>\n' +
                                                                     '                    <div class="status">\n' +
                                                                     '                        <i class="fa fa-circle offline"></i>\n' +
                                                                     '                    </div>\n' +
                                                                     '                </div>\n' +
                                                                     '            </li></a>';
           }
           $('#usersList').html(usersTemplateHTML);
        });
}