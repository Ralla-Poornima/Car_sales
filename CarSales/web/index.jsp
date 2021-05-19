<%-- 
    Document   : index
    Created on : 18 May, 2021, 12:45:05 PM
    Author     : haris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Car_Sales</h1>
          
    <style>

            .container{overflow: hidden}
            .tab{float: left;}
            .tab-2{margin-left: 50px}
            .tab-2 input{display: block;margin-bottom: 10px}
            tr{transition:all .25s ease-in-out}
            tr:hover{background-color:#EEE;cursor: pointer}

       </style>

    </head>
    <body>

        <div class="container">
            <div class="tab tab-1">
                <table id="table" border="1">
                    <tr>
                        <th>Car_name</th>
                        <th>Colour</th>
                        <th>Odometer</th>
                        <th>Doors</th>
                        <th>Price</th>
                    </tr>
                    <tr>
                        <td>Honda</td>
                        <td>Blue</td>
                        <td>123564</td>
                        <td>4</td>
                        <td>5000</td>
                    </tr>

                </table>
            </div>
            <div class="tab tab-2">
                Car_name :<input type="text" name="Car_name" id="Car_name">
                Colour :<input type="text" name="Colour" id="Colour">
                Odometer :<input type="number" name="Odometer" id="Odometer">
                 Doors :<input type="number" name="Doors" id="Doors">
                  Price :<input type="number" name="Price" id="Price">

                <button onclick="addHtmlTableRow();">Add</button>
                <button onclick="editHtmlTbleSelectedRow();">Edit</button>
                <button onclick="removeSelectedRow();">Remove</button>
            </div>
        </div>
        <script>
           
              var rIndex,
                table = document.getElementById("table");

            // check the empty input
            function checkEmptyInput()
            {
                var isEmpty = false,
                Car_name= document.getElementById("Car_name").value,
                    Colour = document.getElementById("Colour").value,
                    Odometer = document.getElementById("Odometer").value;
                     Doors = document.getElementById("Doors").value,
                    Price = document.getElementById("Price").value;


                if(Car_name === ""){
                    alert("Car Name Connot Be Empty");
                    isEmpty = true;
                }
                else if(Colour === ""){
                    alert("Colour Connot Be Empty");
                    isEmpty = true;
                }
                else if(Odometer === ""){
                    alert("Age Connot Be Empty");
                    isEmpty = true;
                }
                 else if(Doors === ""){
                    alert("Doors Connot Be Empty");
                    isEmpty = true;}
                 else if(Price === ""){
                    alert("Price Connot Be Empty");
                    isEmpty = true;
                return isEmpty;
            }

            // add Row
            function addHtmlTableRow()
            {
                // get the table by id
                // create a new row and cells
                // get value from input text
                // set the values into row cell's
                if(!checkEmptyInput()){
                var newRow = table.insertRow(table.length),
                    cell1 = newRow.insertCell(0),
                    cell2 = newRow.insertCell(1),
                    cell3 = newRow.insertCell(2),
                     cell4 = newRow.insertCell(3),
                    cell5 = newRow.insertCell(4),
                    Car_name = document.getElementById("Car_name").value,
                    Colour = document.getElementById("Colour").value,
                    Odometer = document.getElementById("Odometer").value,
                    Doors = document.getElementById("Doors").value,
                    Price = document.getElementById("Price").value;

                cell1.innerHTML = Car_name;
                cell2.innerHTML = Colour;
                cell3.innerHTML = Odometer;
                cell4.innerHTML = Doors;
                cell5.innerHTML = Price;
                // call the function to set the event to the new row
                selectedRowToInput();
            }
            }

            // display selected row data into input text
            function selectedRowToInput()
            {

                for(var i = 1; i < table.rows.length; i++)
                {
                    table.rows[i].onclick = function()
                    {
                      // get the seected row index
                      rIndex = this.rowIndex;
                      document.getElementById("Car_name").value = this.cells[0].innerHTML;
                      document.getElementById("Colour").value = this.cells[1].innerHTML;
                      document.getElementById("Odometer").value = this.cells[2].innerHTML;
                       document.getElementById("Doors").value = this.cells[3].innerHTML;
                      document.getElementById("Price").value = this.cells[4].innerHTML;
                    };
                }
            }
            selectedRowToInput();

            function editHtmlTbleSelectedRow()
            {
                var Car_name = document.getElementById("Car_name").value,
                    Colour = document.getElementById("Colour").value,
                     Odometer= document.getElementById("Odometer").value;
                    Doors = document.getElementById("Doors").value,
                    Price = document.getElementById("Price").value;

               if(!checkEmptyInput()){
                table.rows[rIndex].cells[0].innerHTML = Car_name;
                table.rows[rIndex].cells[1].innerHTML = Colour;
                table.rows[rIndex].cells[2].innerHTML = Odometer;
                 table.rows[rIndex].cells[3].innerHTML = Doors;
                table.rows[rIndex].cells[4].innerHTML = Price;

              }
            }

            function removeSelectedRow()
            {
                table.deleteRow(rIndex);
                // clear input text
                document.getElementById("Car_name").value = "";
                document.getElementById("Colour").value = "";
                document.getElementById("Odometer").value = "";
                 document.getElementById("Doors").value = "";
                document.getElementById("Price").value = "";
            }
        </script>
    </body>
</html>
