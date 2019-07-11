<html xmlns="http://www.w3.org/1999/xhtml" content="text/html;" lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Parts page</title>

</head>
<body>

<form action="parts" method="get">
    Name: <input type="text" name="name"> <br/>
    Number: <input type="text" name="number"> <br/>
    Vendor: <input type="text" name="vendor"> <br/>
    Qty: <input type="text" name="qty"> <br/>
    Snipped: <input type="date" name="snippedStart"> between <input type="date" name="snippedEnd"> <br/>
    Receive: <input type="date" name="receiveStart"> between <input type="date" name="receiveEnd"> <br/>

    <input type="submit" value="FILTER">
</form>
<div>
    <table border=1 class="table_sort">
        <thead>
        <tr>
            <th>Name</th>
            <th>Number</th>
            <th>Vendor</th>
            <th>Qty</th>
            <th>Snipped</th>
            <th>Receive</th>
        </tr>
        </thead>
        <#list parts as part>

            <tr>
                <td>${part.name}
                <td>${part.number}
                <td>${part.vendor}
                <td>${part.qty}
                <td>${part.snipped?iso_utc}
                <td>${part.receive?iso_utc}
            </tr>
        </#list>
    </table>
</div>
<script>
    document.addEventListener('DOMContentLoaded', () => {

        const getSort = ({ target }) => {
        const order = (target.dataset.order = -(target.dataset.order || -1));
    const index = [...target.parentNode.cells].indexOf(target);
    const collator = new Intl.Collator(['en', 'ru'], { numeric: true });
    const comparator = (index, order) => (a, b) => order * collator.compare(
        a.children[index].innerHTML,
        b.children[index].innerHTML
    );

    for(const tBody of target.closest('table').tBodies)
        tBody.append(...[...tBody.rows].sort(comparator(index, order)));

    for(const cell of target.parentNode.cells)
        cell.classList.toggle('sorted', cell === target);
    };

    document.querySelectorAll('.table_sort thead').forEach(tableTH => tableTH.addEventListener('click', () => getSort(event)));

    });
</script>
</body>
</html>