<!-- SNS 공유 스크립트 모음 -->
<!-- 카카오톡 공유 -->

Kakao.init('b13419bc86f056f11717b6453bdbe82b');

function sendLink() {
    Kakao.Link.sendDefault({
        objectType: 'feed',
        content: {
            title: '조근완 천재',
            description: '김윤수도 천재',
            imageUrl: 'https://ifh.cc/g/OjV84n.jpg',
            link: {
                // mobileWebUrl: 'http://211.238.142.93:8585',
                webUrl: 'http://211.238.142.93:8585'
            }
        },
        buttons: [
            {
                title: '놀.러.올.래 ? ?',
                link: {
                    // mobileWebUrl: 'http://211.238.142.93:8585',
                    webUrl: 'http://211.238.142.93:8585'
                }
            },
        ]
    });
}


// SNS 공유 하기 (페이스북 , 트위터, 카카오스토리, 클립보드)

// 페이스북 메타방식 보류
// function facebookShare(url){
//
//     let url1 = url.substring(7);
//
//     console.log(url1+"======================================");
//
//     let encoder = encodeURI(url1);
//     let encodeUrl = encodeURIComponent(url1);
//     let facebook = 'https://www.facebook.com/sharer/sharer.php?u=';
//     let link = facebook + encoder;
//
//     console.log(link);
//     console.log(encoder);
//     console.log(encodeUrl);
//
//     // window.open(link);
//
// }

function snsShare(snsName, link, title) {
    console.log(snsName + " " + link + " " + title);

    /*if (title === null) {
        console.log(snsName+" "+link+" "+title);
        return false;
    }*/

    let currentUrl = jQuery(location).attr('href');

    var snsPopUp;
    var _width = '500';
    var _height = '450';
    var _left = Math.ceil((window.screen.width - _width) / 2);
    var _top = Math.ceil((window.screen.height - _height) / 2);

    let encodeUrl = encodeURIComponent(link);

    console.log("link => " + link);
    console.log("encodeUrl => " + encodeUrl);

    switch (snsName) {
        case 'facebook':
            snsPopUp = window.open("http://www.facebook.com/sharer/sharer.php?u=" + link, '', 'width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top);
            console.log(snsPopUp + "ssssssssssss");
            break;
        case 'twitter' :
            snsPopUp = window.open("http://twitter.com/intent/tweet?url=" + link + "&text=" + title, '', 'width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top);
            break;
        case 'kakao' :
            snsPopUp = window.open("https://story.kakao.com/share?url=" + link, '', 'width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top);
            break;
    }
}

function clipText(link) {
    console.log(link);
    let textArea = document.createElement("textarea");

    // // 임시로 textarea 생성
    // textArea.style.position = 'fixed';
    // textArea.style.top = 0;
    // textArea.style.left = 0;
    // textArea.style.width = '2em';
    // textArea.style.height = '2em';
    // textArea.style.padding = 0;
    // textArea.style.border = 'none';
    // textArea.style.outline = 'none';
    // textArea.style.boxShadow = 'none';
    // textArea.style.background = 'transparent';
    textArea.value = link;
    document.body.appendChild(textArea);

    textArea.select();

    // 복사 시도
    try {
        let successful = document.execCommand('copy');
        if (successful) alert("주소가 복사되었습니다.");
    } catch (err) {
        console.log('Unable to copy');
    }
    // textarea 삭제
    document.body.removeChild(textArea);
}

/********************************************************************************/


/* */
addEventListener("load", function () {
setTimeout(hideURLbar, 0);
}, false);

function hideURLbar() {
    window.scrollTo(0, 1);
}

//
$(function () {
    $("#modal2_btn").click(function () {

        $("#member_id").val('');
        $("#name").val('');
        $("#phone").val('');
        $("#pwd1").val('');
        $("#pwd2").val('');
        $("#idcheck").text('');
        $("#phonecheck").text('');
        $("#success").text('');
        $("#pauth").text('');
        $("#pauth2").text('');
    })
})

//	폰 중복
$(function () {
    $("#phone_btn").click(function () {

        $.ajax({
            type: "post",
            url: "/identify",
            data: {phone: $("#phone").val()},
            datatype: "text",
            success: function (data) {
                var tempData = String(data);
                var datas = tempData.split(' ');
                var certifiNum = datas[0];
                var data1 = datas[1];
                console.log(certifiNum + ' ' + data1);
                if (data1 > 0) {
                    alert("중복된 휴대폰 번호 입니다.")
                    return false;
                } else {
                    alert("인증번호 전송")
                    $('#myModal3').modal("show");
                    //$("#certifiNum").val(certifiNum);
                    $("#numcheck_btn").click(function () {
                        var tempCerti = certifiNum;
                        console.log("디버깅 중입니다~!!!" + $("#number").val() + " " + tempCerti);
                        $("#phonecheck").text("");
                        if ($("#number").val() == tempCerti) {
                            var warningTxt = '<font id="pauth" color="blue">핸드폰 인증이 완료되었습니다.</font>';
                            alert("인증번호 확인완료")

                            $("#phonecheck").append(warningTxt);
                            certifiNum = "";
                            $("#number").text("");
                            $('#myModal3').modal("hide");

                            return false;
                        } else {
                            var warningTxt = '<font id="pauth" color="red">핸드폰 인증에 실패했습니다.</font>';
                            alert("인증번호가 다릅니다.")

                            $("#phonecheck").append(warningTxt);
                            return false;
                            /*$('#myModal3').modal("");*/
                        }
                    })
                }
            }, error: function () {
                alert("data error");
            }
        })
    })
})

$(function () {
    if ($("#name").val() == "") {
        var warningTxt = '<font color="red">이름을 입력하시오.</font>';
        $("#name").focus();
        $("#namecheck").text('');
        $("#namecheck").show();
        $("#namecheck").val(warningTxt);
        return false;
    }
})

//  로그인 버튼을 눌렀을 때
$(function () {
    $("#login_btn").click(function () {

        console.log($("#checkpwd").val() + " 입력한 password");

        $.ajax({
            type: "post",
            url: "/login",
            data: {
                checkid: $("#checkid").val(),
                checkpwd: $("#checkpwd").val()
            },
            datatype: "text",
            success: function (dataTemp) {
                console.log(dataTemp);
                console.log(typeof dataTemp);
                var temp = dataTemp.split(' ');
                var data = Number(temp[0]);
                var username = temp[1];

                console.log(data+" "+username);

                if(data==1){
                    // alert(username+"님 환영합니다.");
                    $("#checkid").text('');
                    $("#checkpwd").text('');
                    $("#signBtn").text('Sign Out');
                    $("#myModal").modal('hide');
                    $(function () {
                        if($("#signBtn").val("Sign Out")){
                            $("#signBtn").click(function () {
                                $.ajax({
                                    type: "post",
                                    url: "/sessionOut",
                                    datatype: "text",
                                    success: function (data) {
                                        if(data!=1){

                                        }
                                        window.location.reload();
                                    },error: function () {
                                        alert("data error")
                                    }
                                })

                            })

                        }
                    })


                    return false;
                }else{
                    alert("로그인 실패")
                    return false;
                }
            }
        })
    })
})

// 구글 연동
function init() {
    console.log('init');
    gapi.load('auth2', function() {
        console.log('auth2');
        window.gauth = gapi.auth2.init({
            client_id: ''
        })

        gauth.then(function() {
            console.log('googleAuth success');
            checkLoginStatus();
        }, function() {
            console.log('googleAuth fail');
        });
        /* Ready. Make a call to gapi.auth2.init or some other API */
    });
}


//
/*function checkLoginStatus() {
    var loginBtn = document.querySelector('#signBtn');

    var checkLogin = loginCheckByLocal();
    console.log('====================');
    console.log(checkLogin);
    console.log('====================');
    /!*var result = checkLogin.replace("<!DOCTYPE html>", "");
    var result2 = result.replace(/(\s*)/g, "");

    if(gauth.isSignedIn.get() || checkLogin == 1) {
        console.log('logined');
        loginBtn.text('Sign Out')

    } else {
        console.log('logouted');
        loginBtn.text('Sign In')
    }

}

function moveToLogin() {
    var signBtn = document.querySelector('#signBtn');

    if(signBtn.value === 'Login') {
        location.href="<%=request.getContextPath()%>/board_loginForm.do";
    }
}

function sessionOut() {
    $.ajax({
        type : "post",
        url : "/sessionOut",
        datatype : "text",
        success: function(data) {
            if(date != 1) {

            }
            window.location.reload();

        }, error: function() {
            alert("data error");
        }
    });
}

function loginCheckByLocal() {
    var result = "";
    $.ajax({
        type : "post",
        url : "/checkSession",
        async: false,
        datatype : "text",
        success: function(data) {
            result = data;

        }, error: function() {
            alert("data error");
        }
    });
    return result;

}*/
//

// 현재 웹문서가 브라우저로 로딩될 때 문서의 본문(body)를 읽고 현재의 JQuery를 호출
$(function () {
    // 회원가입 폼 중에서 아이디중복체크라는 버튼에 마우스를 클릭했을 때 호출되는 무명함수
    $("#idcheck_btn").click(function () {
        $("#idcheck").hide();   // span 태그 idcheck 영역을 숨겨라
        var userId = $("#member_id").val();

        // 입력 길이 체크
        if ($.trim($("#member_id").val()).length < 4) {
            var warningTxt = '<font id="pauth2" color="red">아이디는 4자 이상이어야 합니다.</font>';
            $("#idcheck").text('');   // idcheck 영역 초기화
            $("#idcheck").show();   // span 태그 idcheck 영역을 보이게 하라
            $("#idcheck").append(warningTxt);   //
            $("#member_id").val('').focus();
            return false;
            // } else if ($.trim($("#member_id").val()).length > 40) {
            //     var warningTxt = '<font id="pauth2" color="red">아이디는 40자 이하이어야 합니다.</font>';
            //     $("#idcheck").text('');   // idcheck 영역 초기화
            //     $("#idcheck").show();   // span 태그 idcheck 영역을 보이게 하라
            //     $("#idcheck").append(warningTxt);   //
            //     $("#member_id").val('').focus();
            //     return false;
        }

        // 아이디 중복 여부 확인 - ajax 기술(비동기 통신)
        var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

        $.ajax({
            type: "post",   // data 전송 방식 (get, post)
            url: "/test",    // 파일의 주소와 경로
            data: {userId: $("#member_id").val()},
            datatype: "text",   // 통신할 문서의 데이터 타입
            success: function (data) {
                if (data > 0) {   // 아이디가 중복이고
                    var warningTxt = '<font color="red">중복된 아이디가 존재합니다.</font>';
                    $("#idcheck").text('');   // idcheck 영역 초기화
                    $("#idcheck").show();   // span 태그 idcheck 영역을 보이게 하라
                    $("#idcheck").append(warningTxt);   //
                    $("#member_id").val('').focus();
                    return false;
                } else if (exptext.test($("#member_id").val()) == false) {
                    var warningTxt = '<font id="pauth2" color="red">이메일 형식의 아이디가 아닙니다.</font>';
                    $("#idcheck").text('');   // idcheck 영역 초기화
                    $("#idcheck").show();   // span 태그 idcheck 영역을 보이게 하라
                    $("#idcheck").append(warningTxt);   //
                    $("#member_id").val('').focus();
                    return false;
                } else {
                    var warningTxt = '<font id="pauth2" color="blue">사용가능한 아이디입니다.</font>';
                    $("#idcheck").text('');   // idcheck 영역 초기화
                    $("#idcheck").show();   // span 태그 idcheck 영역을 보이게 하라
                    $("#idcheck").append(warningTxt);   //
                    $("#member_pass").val('').focus();

                    return false;
                }
            },   // 통신이 실패한 경우
            error: function () {   // 데이터 통신이 실패한 경우
                alert("data error" + this.data);
            }
        });   // ajax end


    });
});

$(function () {
    $("#success").hide();
    $("#fail").hide();
    $("#fail2").hide();

    $("#pwd1").keyup(function () {
        let exptext4 = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;

        console.log("#success");
        console.log("#fail");
        console.log("#fail2");

        console.log(exptext4.test($("#pwd1").val())+" 확인 ");
        if (exptext4.test($("#pwd1").val()) == false){
            $("#success").hide();
            $("#fail").hide();
            $("#fail2").show();
            $("#submit_btn").attr("disabled", "disabled");

        } else {
            $("#success").hide();
            $("#fail").hide();
            $("#fail2").hide();
            $("#submit_btn").attr("disabled", "disabled");

            $("#pwd2").keyup(function () {
                var pwd1 = $("#pwd1").val();
                var pwd2 = $("#pwd2").val();

                if (pwd1 != "" || pwd2 != "") {
                    if (pwd1 === pwd2) {
                        $("#success").show();
                        $("#fail").hide();
                        $("#fail2").hide();
                        $("#submit_btn").removeAttr("disabled");
                    } else if(pwd1 != pwd2){
                        $("#success").hide();
                        $("#fail").show();
                        $("#fail2").hide();
                        $("#submit_btn").attr("disabled", "disabled");
                    }
                    // else if ($("#pwd1").val().length > 3) {
                    //     $("#success").hide();
                    //     $("#fail").hide();
                    //     $("#fail2").hide();
                    //     $("#submit").attr("disabled", "disabled");
                    // }
                }
            });
        }
    });
});

