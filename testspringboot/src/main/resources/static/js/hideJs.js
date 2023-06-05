const users_headings = document.querySelectorAll('.users thead th.sort'),
    users_rows = document.querySelectorAll('.users tbody tr.common');

function change(){
    if (document.getElementById('usr').style.getPropertyValue('display') == 'block'){
        document.getElementById('usr').style.display='none';
        document.getElementById('see').textContent = 'Добавить'
    } else{
        document.getElementById('usr').style.display='block';
        document.getElementById('see').textContent = 'Скрыть'
    }   
}

function see(id){
    if (document.getElementById(id).style.getPropertyValue('display') == 'block'){
        document.getElementById(id).style.display='none';
        document.getElementById(id+'_img').src = '/imgs/close_arrow.png'
    } else{
        document.getElementById(id).style.display='block';
        document.getElementById(id+'_img').src = '/imgs/open_arrow.png'
    }   
}

document.getElementById("addTovar").onclick = function() {
    document.getElementById("tovarForm").submit();
}

function updateTovar(id){
    document.getElementById(id).submit();
}


users_headings.forEach((head, i) =>{
    head.onclick = ()=>{
        users_headings.forEach(head => head.classList.remove('active_header'));
        head.classList.add('active_header');
        head.classList.toggle('asc');

        let sort_asc = head.classList.contains('asc')? true:false;
        sortTable(i, sort_asc);
    }
})

function sortTable(column, sort_asc){
    [...users_rows].sort((a, b)=>{
        let first_row="", second_row="";
        
        if (column < 2){
            first_row = parseInt(a.querySelectorAll(column==0? 'th':'td')[0].textContent, 10);
            second_row = parseInt(b.querySelectorAll(column==0? 'th':'td')[0].textContent, 10);
        } else{
            if (column%5 == 0){
                first_row = (a.querySelectorAll('td input')[column-2].checked) + "";
                second_row = (b.querySelectorAll('td input')[column-2].checked) + "";
            } else{
                first_row = a.querySelectorAll('td input')[column-2].value.toLowerCase();
                second_row = b.querySelectorAll('td input')[column-2].value.toLowerCase();
            }
        }        

        return sort_asc? (first_row < second_row? -1:1):(first_row < second_row? 1:-1);
    })
        .map(sorted_row => document.querySelector('tbody').appendChild(sorted_row));
}