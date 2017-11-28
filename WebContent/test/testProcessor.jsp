<%@page import="club.seedymusic.processor.CurrencyTools"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   <title>Processor Test</title>
</head>
<body>
   
<jsp:include page="/assets/fragments/libs.jsp"/>

      <h1>Processor Test</h1>
      
      <div>
      <table class="table table-inverse" style="width: 50%">
       <tr>
         <th>Test Case</th>
         <th>Result</th>
       </tr>

       <tr>
         <td>Converting $1 CAD to XBT</td>
         <td>${CurrencyTools.getValueXBT(20.0)}</td>
       </tr>       
       <tr>
         <td>Converting $10 CAD to XBT</td>
         <td>${CurrencyTools.getValueXBT(20.0)}</td>
       </tr>
       <tr>
         <td>Converting $100 CAD to XBT</td>
         <td>${CurrencyTools.getValueXBT(100.0)}</td>
       </tr>
       <tr>
         <td>Converting $1000 CAD to XBT</td>
         <td>${CurrencyTools.getValueXBT(1000.0)}</td>
       </tr>
       <tr>
         <td>Converting $10,000 CAD to XBT</td>
         <td>${CurrencyTools.getValueXBT(10000.0)}</td>
       </tr>
       
       
      </table>
      </div>
   </body>
</html>
