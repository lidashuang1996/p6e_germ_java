<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="./js/jquery-2.1.1.min.js"></script>
    <style type="text/css">
        * {
            padding: 0;
            margin: 0;
        }
        .main {
            width: 100%;
            height: 100vh;
            position: relative;
        }
        .main-top {
            width: 100%;
            height: 36vh;
            background-color: rgb(60 129 228);
        }
        .main-top .main-top-title {
            width: 960px;
            height: 80%;
            margin: 0 auto;
            color: #ffffff;
            opacity: 0.05;
            font-size: 160px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .main-bottom {
            width: 100%;
            height: 64vh;
            background-color: rgb(246 246 246);
        }

        .main-bottom .main-bottom-copyright {
            position: absolute;
            bottom: 10px;
            width: 700px;
            text-align: center;
            left: 50%;
            font-size: 12px;
            color: #adadad;
            transform: translateX(-50%);
        }

        .main-content {
            width: 560px;
            background-color: #ffffff;

            position: absolute;
            left: 50%;
            top: 30vh;
            transform: translateX(-50%);
            border-radius: 6px;

            padding: 24px 0;

            box-shadow: 0 0 16px 0 rgba(204, 204, 204, 0.6);
        }

        .main-content-title {
            position: absolute;
            top: -60px;
            width: 100%;
            height: 60px;
            color: #ffffff;
            box-sizing: border-box;
            padding: 0 16px;

            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .main-content-title .title {
            font-size: 32px;
            height: 100%;
            display: flex;
            align-items: center;
        }
        .main-content-title .subtitle {
            font-size: 16px;
            height: 100%;
            display: flex;
            align-items: flex-end;
            margin-bottom: 20px;
        }

        .main-content-input {
            width: 400px;
            margin: 20px auto;
            padding-bottom: 4px;
            border-bottom: 2px solid #262626;
        }
        .main-content-input label {
            display: flex;
            align-items: center;
            justify-content: flex-start;
            width: 100%;
            height: 36px;
            font-size: 22px;
            color: #262626;
        }

        .main-content-input input {
            width: 100%;
            height: 32px;
            font-size: 18px;
            border: none;
            outline: none;
            padding: 0 2px;
            color: #262626;
            margin-top: 6px;
        }

        .main-content-error {
            color: red;
            width: 400px;
            height: 20px;
            margin: 0 auto;
            font-size: 16px;

            display: flex;
            align-items: center;
            justify-content: flex-start;
        }

        .main-content-button {
            width: 400px;
            height: 48px;
            margin: 20px auto;
        }
        .main-content-button button {
            width: 100%;
            height: 100%;
            cursor: pointer;
            outline: none;
            border: none;
            color: #ffffff;
            font-size: 20px;
            border-radius: 4px;
            background: #3C81E4;
            display: flex;
            justify-content: center;
            align-items: center;
        }

    </style>
</head>
<body>
<div class="main">
    <!-- 上部的内容 -->
    <div class="main-top">
        <p class="main-top-title">P6e Oauth2</p>
    </div>
    <!-- 下部的内容 -->
    <div class="main-bottom">
        <p class="main-bottom-copyright">p6e oauth2. github https://github.com/lidashuang1996</p>
    </div>
    <div class="main-content">
        <div class="main-content-title">
            <p class="title">P6e Oauth2 登录</p>
            <p class="subtitle">Oauth2 授权码模式</p>
        </div>
        <div class="main-content-input" id="account">
            <label id="account-title">
                账号
            </label>
            <label>
                <input name=""
                       type="text"
                       placeholder="请输入账号（邮箱/手机号）"
                       id="account-input"
                       onkeyup="confirm()"
                       onfocus="inputFocus('account')"
                       onblur="inputBlur('account')"/>
            </label>
        </div>
        <div class="main-content-input" id="password">
            <label id="password-title">
                密码
            </label>
            <label>
                <input name=""
                       type="password"
                       placeholder="请输入密码"
                       id="password-input"
                       onkeyup="confirm()"
                       onfocus="inputFocus('password')"
                       onblur="inputBlur('password')"/>
            </label>
        </div>
        <div class="main-content-error" id="error">账号不能为空</div>
        <div class="main-content-button" id="button">
            <button onclick="confirm('LOGIN')" id="button-box">
                <span id="button-text1">登 录</span>
                <svg id="button-svg" style="width: 48px; height: 48px; display: none;" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" viewBox="0 0 100 100" enable-background="new 0 0 0 0" xml:space="preserve">
						<path fill="#fff" d="M73,50c0-12.7-10.3-23-23-23S27,37.3,27,50 M30.9,50c0-10.5,8.5-19.1,19.1-19.1S69.1,39.5,69.1,50" transform="rotate(273.148 50 50)">
                            <animateTransform attributeName="transform" attributeType="XML" type="rotate" dur="1s" from="0 50 50" to="360 50 50" repeatCount="indefinite"></animateTransform>
                        </path>
					</svg>
                <span id="button-text2" style="display: none;">登录中...</span>
            </button>
        </div>
    </div>
</div>
<script type="text/javascript">
    let isLogin = false;

    function inputFocus(name) {
        document.getElementById(name + '-title').style.color = 'rgb(60 129 228)';
        document.getElementById(name).style.borderBottom = '2px solid rgb(60 129 228)';
    }

    function inputBlur(name) {
        document.getElementById(name + '-title').style.color = '#262626';
        document.getElementById(name).style.borderBottom = '2px solid #262626';
    }

    function confirm(e) {
        if (isLogin) {
            return;
        }
        const event = e || window.event; //在火狐下event会做为参数传进来，ie下会在window下
        // e.which是火狐下获取keyCode的方式，ie下使用e.keyCode获取
        const keyCode = event.which || event.keyCode || (e === 'LOGIN' && 13);
        const account = document.getElementById('account-input').value;
        const password = document.getElementById('password-input').value;
        if (account.length === 0) {
            document.getElementById('error').innerText = '账号不能为空';
            document.getElementById('error').style.display = 'block';
            document.getElementById('button').style.marginTop = '20px';
            return;
        } else if (account.length < 5) {
            document.getElementById('error').innerText = '账号长度不能低于5位';
            document.getElementById('button').style.marginTop = '20px';
            document.getElementById('error').style.display = 'block';
            return;
        } else if (password.length === 0) {
            document.getElementById('error').innerText = '密码不能为空';
            document.getElementById('button').style.marginTop = '20px';
            document.getElementById('error').style.display = 'block';
            return;
        } else if (password.length < 6) {
            document.getElementById('error').innerText = '密码长度不能低于6位';
            document.getElementById('button').style.marginTop = '20px';
            document.getElementById('error').style.display = 'block';
            return;
        }
        document.getElementById('error').innerText = '';
        document.getElementById('button').style.marginTop = '40px';
        document.getElementById('error').style.display = 'none';
        if (keyCode === 13) {
            loginAjax({
                account: account,
                password: password
            });
            document.getElementById('button-text1').style.display = 'none';
            document.getElementById('button-text2').style.display = 'block';
            document.getElementById('button-svg').style.display = 'block';
        }
    }

    /**
     * 登陆的 ajax 请求
     */
    function loginAjax(data) {
        isLogin = true;
        $.ajax({
            type: 'POST',
            url: '/sign/in',
            contentType: 'application/json',
            data: JSON.stringify(data),
            dataType: 'JSON',
            success: (res) => {
                isLogin = false;
                console.log(res);
                document.getElementById('button-text1').style.display = 'block';
                document.getElementById('button-text2').style.display = 'none';
                document.getElementById('button-svg').style.display = 'none';
            }, error: (e) => {
                isLogin = false;
                document.getElementById('error').innerText = '网络异常，请稍后重试';
                document.getElementById('button-text1').style.display = 'block';
                document.getElementById('button-text2').style.display = 'none';
                document.getElementById('button-svg').style.display = 'none';
                console.log(e);
            }
        });
    }
</script>
</body>
</html>
