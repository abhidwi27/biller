$(document)
		.ready(
				function() {

					$("#SignIn").on(
							'click',
							function() {
								var Email = $("#Email").val();
								var Password = $("#Password").val();
								if (Email == "") {
									$('#messageDiv').css("display", "none");
									alert("Email ID is required");
									return;
								}
								if (Password == "") {
									$('#messageDiv').css("display", "none");
									alert("Password is required");
									return;
								}
								$.ajax({
									url : '/invoice/login/process.do?userid='
											+ Email + "&password=" + Password,
									type : 'POST',
									dataType : 'text',
									success : function(results) {
										alert(results);

									}
								});
							});

					function ShowMessage(results) {
						if (results == 'SUCCESS') {
							$('#messageDiv')
									.html(
											"<font color='green'>You are successfully logged in. </font>")
						} else if (results == 'FAILURE') {
							$('#messageDiv')
									.html(
											"<font color='red'>Email ID or password incorrect </font>")
						}
					}
					$('#Email').on('blur', function() {
						if ($(this).val().length < 6) {
							alert('Email ID should be atleast 6 characters!');
						}
					});

					$('#Password').on('blur', function() {
						if ($(this).val().length < 8) {
							alert('Password should be atleast 8 characters!');
						}
					});
					$("#loginSubmit").click(function(){
						$("#loginForm").submit();
					});
				});