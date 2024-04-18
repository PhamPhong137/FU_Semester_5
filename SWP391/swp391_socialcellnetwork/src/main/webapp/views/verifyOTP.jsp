
<%-- 
    Document   : profile
    Created on : Jan 16, 2024, 2:23:26 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>SCN | OTP</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="templates/css/bootstrap.min.css">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="css/verifyotp.css">

    </head>

    <body>
        <div class="container-fluid body-content">
            <div class="row">
                <div class="col-lg-12">
                    <div class="body-content-reset">
                        <h1>Verify</h1> 
                        <p style="margin-bottom: 5px">A verification code has been sent to</p> 
                        <p style="font-weight: bold">${email}</p>
                        <p>Please check your inbox and enter the verification code below to verify your email address. 
                            The code will expire in <span style="color: red" id="time"></span></p>
                        <form action="forgotpassword" method="post">  
                            <input type="hidden" name="email" value="${email}">
                            <input type="hidden" name="otp" value="${otp}"> 
                            <div id="otp-container">
                                <input type="text" class="otp-input" maxlength="1" name="otp1" required/>
                                <input type="text" class="otp-input" maxlength="1" name="otp2" required/>
                                <input type="text" class="otp-input" maxlength="1" name="otp3" required/>
                                <input type="text" class="otp-input" maxlength="1" name="otp4" required/>
                                <input type="text" class="otp-input" maxlength="1" name="otp5" required/>
                                <input type="text" class="otp-input" maxlength="1" name="otp6" required/>
                                <div id="error-message" class="error-message" style="color: red; margin: 20px 0px 10px 0px">${errorCode}</div>
                            </div>
                            <button type="submit">Verify</button>
                        </form>
                        <div style="display: flex; justify-content: space-between">
                            <a id="resendLink" href="forgotpassword?email=${email}&resendcode">Resend code</a>
                            <a href="forgotpassword">Change email</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>

            const otpInputs = document.querySelectorAll('.otp-input');
            const errorMessage = document.getElementById('error-message');

            otpInputs.forEach((input, index) => {
                input.addEventListener('input', (event) => {
                    let value = event.target.value;

                    if (!/^\d*$/.test(value)) {
                        displayErrorMessage('Please input number!');
                        event.target.value = '';
                    } else {
                        hideErrorMessage();
                        if (value !== '' && index < otpInputs.length - 1) {
                            otpInputs[index + 1].focus();
                        } else {
                            otpInputs[index].blur();
                        }
                    }
                });

                input.addEventListener('keydown', (event) => {
                    const key = event.key;

                    if (/^\d$/.test(key)) {
                        event.preventDefault();
                        input.value = key;

                        if (index < otpInputs.length - 1) {
                            otpInputs[index + 1].focus();
                        } else {
                            otpInputs[index].blur();
                        }
                    }

                    if (event.key === 'ArrowLeft' && index > 0) {
                        otpInputs[index - 1].focus();
                    }

                    if (event.key === 'ArrowRight' && index < otpInputs.length - 1) {
                        otpInputs[index + 1].focus();
                    }

                    if (event.key === 'Backspace' && index > 0 && event.target.value === '') {
                        otpInputs[index - 1].focus();
                    }
                });

                input.addEventListener('focus', () => {
                    input.select();
                });
            });

            function displayErrorMessage(message) {
                errorMessage.textContent = message;
                setTimeout(() => {
                    hideErrorMessage();
                }, 2000);
            }

            function hideErrorMessage() {
                errorMessage.textContent = '';
            }

            let timeLeft = 120;
            let countdownElement = document.getElementById("time");

            let countdownInterval = setInterval(function () {
                timeLeft--;
                let minutes = Math.floor(timeLeft / 60);
                let seconds = timeLeft % 60;

                countdownElement.textContent = (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds;

                if (timeLeft <= 0) {
                    clearInterval(countdownInterval);
                    window.location.href = "forgotpassword";
                }
            }, 1000);

        </script>
        <!-- jquery-->
        <script src="templates/js/jquery.min.js"></script>
        <!-- Bootstrap js -->
        <script src="templates/js/bootstrap.min.js"></script>
        <!-- Validator js -->
        <script src="templates/js/validator.min.js"></script>
        <!-- Custom Js -->
        <script src="js/main.js"></script>


    </body>



</html>

