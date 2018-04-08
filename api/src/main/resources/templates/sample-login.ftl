<!doctype html>
<head>
    <meta charset="utf-8">
    <title>login</title>
</head>

<body>
<div>
    <input type="hidden" id="country_code" value="+82"/>
    <input type="hidden" id="phone_num"/>
    <button onclick="phone_btn_onclick();">핸드폰으로 로그인</button>
    <br/>
    <input type="hidden" id="email" = value=""/>
    <button onclick="email_btn_onclick();">이메일로 로그인</button>
</div>

<!--  -->
<script src="https://sdk.accountkit.com/ko_KR/sdk.js"></script>
<script>
    // initialize Account Kit with CSRF protection
    AccountKit_OnInteractive = function () {
        AccountKit.init(
                {
                    appId: "157869001559967",
                    state: "0",
                    version: "v1.1"
                }
        );
    };

    // login callback
    function loginCallback(response) {
        console.log('loginCallback');
        if (response.status === "PARTIALLY_AUTHENTICATED") {
            post(response.code);
        }
        else if (response.status === "NOT_AUTHENTICATED") {
            // handle authentication failure
            alert('login fail');
        }
        else if (response.status === "BAD_PARAMS") {
            // handle bad parameters
            alert('login fail');
        }
    }

    // phone form submission handler
    function phone_btn_onclick() {
        var country_code = document.getElementById("country_code").value;
        var ph_num = document.getElementById("phone_num").value;
        AccountKit.login('PHONE',
                {countryCode: country_code, phoneNumber: ph_num}, // will use default values if this is not specified
                loginCallback);
    }

    // email form submission handler
    function email_btn_onclick() {
        var email_address = document.getElementById("email").value;
        AccountKit.login('EMAIL', {emailAddress: email_address}, loginCallback);
    }

    function post(code) {
        function reqListener() {
            console.log(this.response);
        }

        var newXHR = new XMLHttpRequest();

        newXHR.addEventListener('load', reqListener);

        newXHR.open('POST', 'http://localhost:8080/api/v1/users');
        newXHR.setRequestHeader("Content-Type", "application/json");

        var jsonData = {code: code};

        var formattedJsonData = JSON.stringify(jsonData);

        newXHR.send(formattedJsonData);
    }
</script>
</body>