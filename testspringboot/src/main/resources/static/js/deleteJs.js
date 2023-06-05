function remove(prefix, command, id, btn){
    var form = document.getElementById(btn);
    $.ajax({
       url: '\\'+command+'\\' + id,
       success: function(result) {
        document.getElementById(prefix + id).remove();
       }, error: function(error) {
        alert("error");
       }
    })
}

function addUser(){
    var str = $("#userRoleForm").serialize();
    $.ajax({
        data: str,
        url: '/addUserAdmin',
       success: function(result) {
        location.reload();
       }, error: function(error) {
        alert("error");
       }
    })
}


function update(command, id){
    var str = $("#"+id).serialize();
    $.ajax({
        data: str,
        url: '\\'+command,
       success: function(result) {
       }, error: function(error) {
        alert("error");
       }
    });
}

