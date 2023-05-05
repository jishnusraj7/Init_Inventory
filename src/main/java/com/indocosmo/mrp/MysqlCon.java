package com.indocosmo.mrp;

import java.sql.*;  
class MysqlCon{  
public static void main(String args[]){  
try{  
Class.forName("com.mysql.jdbc.Driver");  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://192.168.1.250:3306/central_cntrl","central","posella123456^.");  

Statement stmt=con.createStatement();  
Statement stmt2=con.createStatement();
Statement stmt3=con.createStatement();
Statement stmt4=con.createStatement();
Statement stmt5=con.createStatement();
Statement stmt6=con.createStatement();
ResultSet rs=stmt.executeQuery("select * from stock_items WHERE is_deleted NOT IN (1)");  
int i=0;
int j=0;
while(rs.next()){
	//System.out.println(rs.getInt(1)); 
	
	ResultSet depRs=stmt2.executeQuery("SELECT id FROM mrp_department WHERE is_deleted=0");
	while(depRs.next()){
		
		//System.out.println("Dep========"+depRs.getInt(1)); 
		//System.out.println("inside==========>"+rs.getInt(1)); 
		String  sqls="SELECT dtl.department_id,dtl.stock_item_id,dtl.in_qty,dtl.cost_price FROM mrp_stock_register_dtl dtl JOIN mrp_stock_register_hdr hdr  ON dtl.stock_register_hdr_id=hdr.id"
				+" WHERE dtl.in_qty>0.00 and dtl.department_id="+depRs.getInt(1)+" AND dtl.stock_item_id="+rs.getInt(1)+" AND  dtl.cost_price>0.00 ORDER BY hdr.created_at DESC LIMIT 1";
			//System.out.println("sql===============>"+sql);
		
		ResultSet rs2=stmt3.executeQuery(sqls);
	
		String sql2 = "SELECT vw.current_stock FROM vw_itemstock vw INNER JOIN mrp_department dept ON vw.department_id = dept.id WHERE vw.`stock_item_id`="+rs.getInt(1)+" AND vw.`department_id`="+depRs.getInt(1)+"";
		ResultSet rs5=stmt5.executeQuery(sql2);
		boolean regIsEmpty = ! rs2.first();
		boolean vmItemStockIsEmpty2 = ! rs5.first();
			if (regIsEmpty || vmItemStockIsEmpty2 ) { 
				
				//System.out.println("yes, "+depRs.getInt(1)+", "+rs.getInt(1)+", 0, 0, "+rs.getInt(15)+", 1, "+rs.getInt(15));
				i++;
				String  sql = "INSERT INTO mrp_dept_stock_item_test VALUES ("+i+", "+depRs.getInt(1)+", "+rs.getInt(1)+", 0.00, 0.00,"+rs.getInt(15)+", 0 , "+rs.getInt(15)+", now(), null, 0,'empty')";
		       
			stmt4.executeUpdate(sql);
			}else{
			
				if(rs5.getDouble(1)>0.00){
					j++;
				System.out.println("no, "+depRs.getInt(1)+", "+rs.getInt(1)+", 0, 0, "+rs2.getDouble(4)+", "+rs5.getDouble(1)+", "+rs2.getDouble(4));
				String  sql = "INSERT INTO mrp_dept_stock_item VALUES ("+j+", "+depRs.getInt(1)+", "+rs.getInt(1)+", 0.00, 0.00,"+rs2.getDouble(4)+", "+rs5.getDouble(1)+", "+rs2.getDouble(4)+", now(), null, 0)";
				stmt5.executeUpdate(sql);
				}else{
					i++;
					String  sql = "INSERT INTO mrp_dept_stock_item_test VALUES ("+i+", "+depRs.getInt(1)+", "+rs.getInt(1)+", 0.00, 0.00,"+rs2.getDouble(4)+", "+rs5.getDouble(1)+", "+rs2.getDouble(4)+", now(), null, 0,'zero or negative')";
					stmt4.executeUpdate(sql);					
				}
				
			}

					
	}
	
}
con.close();  
}catch(Exception e){ System.out.println(e);}  
}  
}  