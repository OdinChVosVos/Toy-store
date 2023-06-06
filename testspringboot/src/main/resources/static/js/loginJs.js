// function addUser(){
//     var str = $("#addUser").serialize();
//     $.ajax({
//         data: str,
//         url: '/addUser',
//        success: function(result) {
//         alert("success");
//        }, error: function(error) {
//         alert("error");
//        }
//     })
// }

// $(document).ready(function() {
//     $("#addUser").submit(function(e) {
//       e.preventDefault();
//       var str = $("#addUser").serialize();
//       $.ajax({
//         url: '/addUser',
//         data: str,
//         success: function(result) {
//             window.location.href = "/main";
//         }, error: function(error) {
//             alert("Не верно введены данные, либо пользователь уже есть");
//         }
//       });
//       return false;
//     });
// });