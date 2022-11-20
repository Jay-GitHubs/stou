// Anuwat Yodngoen 6596006889
// Coffee Shop (POS)
import java.util.Scanner;

public class Homework1 {
	
	
	static CoffeeInfo[] getCoffeeList() {
		
		CoffeeInfo[] coffeeList = new CoffeeInfo[3];
		
		coffeeList[0] = new CoffeeInfo();
		coffeeList[0].Name = "Americano";
		coffeeList[0].Price = 40;
		
		coffeeList[1] = new CoffeeInfo();
		coffeeList[1].Name = "Latte";
		coffeeList[1].Price = 45;
		
		coffeeList[2] = new CoffeeInfo();
		coffeeList[2].Name = "Cappuccino";
		coffeeList[2].Price = 50;
		
		
		return coffeeList;
		
	}
	
	public static void main(String[] args) {
		
		CoffeeInfo[] coffeeList = getCoffeeList();
		Order order = new Order();
		
		Scanner sc = new Scanner(System.in);
		boolean IsAppRunning = true;
		
		OrderProcessor op = new OrderProcessor();
		op.AppMode = OrderProcessor.ProcessMode.StandBy;
		
		ScreenManager scm = new ScreenManager(op);
		scm.drawMenuList();
		
		
		while (IsAppRunning) {
			
			
			String inputStr = sc.next();
			int choice = 0;
			
			switch(inputStr) {
			
			case "1": 
				choice =1;
				//Protection of press number on the order mode
				if(op.AppMode == OrderProcessor.ProcessMode.StandBy) {
					scm.drawBeverageMenu(coffeeList);
					op.OderingMode = OrderProcessor.OderMode.StandBy;
				}else if(op.AppMode == OrderProcessor.ProcessMode.Ordering) {
					//Input number of coffee
					if(op.OderingMode == OrderProcessor.OderMode.StandBy) {
						order.CurrentOderItem = new OrderItem();
						order.CurrentOderItem.CoffeeIndex = 0;
						scm.showMessage(String.format("The number of %s cups -> ", coffeeList[0].Name), true);
						op.OderingMode = OrderProcessor.OderMode.WaitingNumber;
					}else {
						order.CurrentOderItem.NumberOfItems = 1;
						order.CurrentOderItem.TotalPrice 
								= (order.CurrentOderItem.NumberOfItems * coffeeList[order.CurrentOderItem.CoffeeIndex].Price);
						
						order.showCurrentBill(order.CurrentOderItem, scm);
						scm.drawLine(40, false);
						scm.showMessage("Your choice -> ", true);
						
					}
				}
				break;
			case "2": 
				choice =2;
				//Protection of press number on the report mode
				if(op.AppMode == OrderProcessor.ProcessMode.StandBy) {	
					if(order.CurrentOderItem == null) {
						scm.showMessage("You have no order to show report!!");
						scm.showMessage("Your choice -> ", true);
					}else {
						scm.drawReport(order, coffeeList);
					}
				}else if(op.AppMode == OrderProcessor.ProcessMode.Ordering) {
					//Input number of coffee
					if(op.OderingMode == OrderProcessor.OderMode.StandBy) {
						order.CurrentOderItem = new OrderItem();
						order.CurrentOderItem.CoffeeIndex = 1;
						scm.showMessage(String.format("The number of %s cups -> ", coffeeList[order.CurrentOderItem.CoffeeIndex].Name));
						op.OderingMode = OrderProcessor.OderMode.WaitingNumber;
					}else {
						order.CurrentOderItem.NumberOfItems = 2;
						order.CurrentOderItem.TotalPrice 
								= (order.CurrentOderItem.NumberOfItems * coffeeList[order.CurrentOderItem.CoffeeIndex].Price);
						
						order.showCurrentBill(order.CurrentOderItem, scm);
						scm.drawLine(40, false);
						scm.showMessage("Your choice -> ", true);
					}
				}
				break;
			case "3": 
				choice =3;
				//Protection of press number on the other mode
				if(op.AppMode == OrderProcessor.ProcessMode.StandBy) {				
					scm.drawMenuList();
				}else if(op.AppMode == OrderProcessor.ProcessMode.Ordering) {
					//Input number of coffee
					if(op.OderingMode == OrderProcessor.OderMode.StandBy) {
						order.CurrentOderItem = new OrderItem();
						order.CurrentOderItem.CoffeeIndex = 2;
						scm.showMessage(String.format("The number of %s cups -> ", coffeeList[0].Name));
						op.OderingMode = OrderProcessor.OderMode.WaitingNumber;
					}else {
						order.CurrentOderItem.NumberOfItems = 3;
						order.CurrentOderItem.TotalPrice 
								= (order.CurrentOderItem.NumberOfItems * coffeeList[order.CurrentOderItem.CoffeeIndex].Price);
						
						order.showCurrentBill(order.CurrentOderItem, scm);
						scm.drawLine(40, false);
						scm.showMessage("Your choice -> ", true);
					}
				}
				break;				
			case "9": 
				choice =9;
				if(op.AppMode == OrderProcessor.ProcessMode.Ordering) {
					//Input number of coffee
					if(op.OderingMode == OrderProcessor.OderMode.WaitingNumber) {
						
						order.CurrentOderItem.NumberOfItems = 9;
						order.CurrentOderItem.TotalPrice 
								= (order.CurrentOderItem.NumberOfItems * coffeeList[order.CurrentOderItem.CoffeeIndex].Price);
						
						order.showCurrentBill(order.CurrentOderItem, scm);
						scm.drawLine(40, false);
						scm.showMessage("Your choice -> ", true);
						
					}
				}
				break;
				default:
					//Protection of press any number on the standby mode
					if(op.AppMode == OrderProcessor.ProcessMode.StandBy) {
						scm.drawMenuList();			
					}else if (op.AppMode == OrderProcessor.ProcessMode.Ordering) {
						if(op.OderingMode == OrderProcessor.OderMode.StandBy) {
							scm.showMessage("You made the wrong choice; Please try again.!!");
							scm.showMessage("Your choice -> ", true);
						}else {
							
						   try{
								order.CurrentOderItem.NumberOfItems = Integer.parseInt(inputStr);
								order.CurrentOderItem.TotalPrice 
									= (order.CurrentOderItem.NumberOfItems * coffeeList[order.CurrentOderItem.CoffeeIndex].Price);
								
								order.showCurrentBill(order.CurrentOderItem, scm);
								scm.drawLine(40, false);
								scm.showMessage("Your choice -> ", true);
					        }
					        catch (NumberFormatException ex){
								scm.showMessage("You made the wrong number; Please try again.!!");
								scm.showMessage("Your number is -> ", true);
					        }
								
						}
	
					}else if(op.AppMode == OrderProcessor.ProcessMode.Reporting) {
						scm.showMessage("You made the wrong choice; Please try again.!!");
						scm.showMessage("Your choice -> ", true);
					}
					
					break;
			
			}
			
			if((choice == 9) && (op.AppMode == OrderProcessor.ProcessMode.StandBy)) {
				
				scm.showMessage("Are you sure to exit application?[Y/N] -> ", true);
				char confirmChar = sc.next().charAt(0);
				
				if(confirmChar == 'Y') {
					IsAppRunning = false;
				}
			} else if ((choice == 9) && (op.AppMode != OrderProcessor.ProcessMode.StandBy)) {
				order.addOrderItem(order.CurrentOderItem);
				scm.drawMenuList();	
				op.AppMode = OrderProcessor.ProcessMode.StandBy;
			}
						
		}
		
		scm.clearScreen();
		sc.close();
		String star = scm.getStar(5);
		scm.showMessage(String.format("%s \t See Bye, See you soon %s", star, star));
		System.exit(0);
	}


}

class Order{
	
	public Order() {
		this.OrderList = new OrderItem[100];
	}
	
	OrderItem[] OrderList;
	
    public OrderItem CurrentOderItem;

	public OrderItem getCurrentOderItem() {
		return CurrentOderItem;
	}

	public void setCurrentOderItem(OrderItem currentOderItem) {
		CurrentOderItem = currentOderItem;
	}
	
	public void addOrderItem(OrderItem orderItem) {
		for(int i=0; i< OrderList.length; i++) {
			if(OrderList[i] == null) {
				OrderList[i] = orderItem;
				break;
			}
		}
	}
	
	public void showCurrentBill(OrderItem orderItem, ScreenManager sm) {
		
		String star = sm.getStar(4);
		
		sm.showMessage(String.format("%s Your bill -> %s baht %s", star, orderItem.TotalPrice, star ));
	}
	
}

class OrderItem{
	
	public int NumberOfItems;
	public int CoffeeIndex;
	public int TotalPrice;
	
}

class OrderProcessor{
	
	
	public enum ProcessMode{
		
		StandBy,
		Ordering,
		Reporting
	}
	
	public enum OderMode{
		
		StandBy,
		WaitingNumber
	}
	
	public ProcessMode AppMode;
	
	public OderMode OderingMode;

	public OderMode getOderingMode() {
		return OderingMode;
	}

	public void setOderingMode(OderMode oderingMode) {
		OderingMode = oderingMode;
	}

	public ProcessMode getAppMode() {
		return AppMode;
	}

	public void setAppMode(ProcessMode appMode) {
		AppMode = appMode;
	}
	
	
	
	
	
}

class CoffeeInfo{
	
	public String Name;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public int Price;
	
}

//This class for manipulate the screen output
class ScreenManager{
	
	OrderProcessor op;
	
	public ScreenManager(OrderProcessor orderProcessor) {
		
		this.op = orderProcessor;
		
	}
	
	//Default of line's length
	int DefaultLineLength = 50;
	
	//Default of star's length
	int DefaultStarLength = 50;
	
	//For drawing menu list
    public void drawMenuList() {
    	
    	clearScreen();
    	
		drawLine(DefaultLineLength, false);
		showMessage("Welcome to Anuwat Yodngoen's Cafe(6596006889)");
		drawLine(DefaultLineLength, false);
		showMessage( getStar(3) 
				+ "Please choose your choice by \n\t pressing the number on the keyboard." 
				+ getStar(3) + "\n" );
		

		showMessage("1 -> Order");
		showMessage("2 -> Report");
		drawLine(DefaultLineLength, true);
		showMessage("\t\t Please press number 9 -> Exit");
		drawLine(DefaultLineLength, false);
		showMessage("Your choice -> ", true);
	}
    
	//For drawing beverage menu
    public void drawBeverageMenu(CoffeeInfo[] arrayCoffee) {
    	
    	clearScreen();
		drawLine(DefaultLineLength, false);
		showMessage("Beverage Menu");
		drawLine(DefaultLineLength, false);
		
		showMessage( getStar(3) 
				+ "Please choose the offee your likes by \n\t pressing the number on the keyboard." 
				+ getStar(3) + "\n" );
		
		String tab = "\t";
		for(int i=0; i< arrayCoffee.length; i++) {
			
			
			
			if(i == 2) {
				tab = "";
			}
			
			showMessage(String.format("%s -> %s %s %s baht", (i+1), arrayCoffee[i].Name, tab ,arrayCoffee[i].Price));	
		}
		drawLine(DefaultLineLength, true);
		showMessage(" Back to main menu please press number 9 -> Quit");
		drawLine(DefaultLineLength, false);
		showMessage("Your choice -> ", true);
		
		op.AppMode = OrderProcessor.ProcessMode.Ordering;
	}
    
    
	//For drawing beverage menu
    public void drawReport(Order order, CoffeeInfo[] coffeeList) {
    	
    	clearScreen();
    	
    	String star = getStar(10);
    	
		drawLine(DefaultLineLength, false);
		showMessage(String.format("%s Total Orders Today %s baht", star,star));	
		drawLine(DefaultLineLength, false);
		
		int currentItem = 0;
		int netTotal = 0;
		for(int i=0; i< order.OrderList.length; i++) {
			if(order.OrderList[i] != null) {
				String cup = "cup";
				if(order.OrderList[i].NumberOfItems > 1) {
					cup = "cups";
				}
				currentItem++;
				netTotal += order.OrderList[i].TotalPrice;
				showMessage(String.format("%s ) %s %s %s \t\t %s baht",currentItem, coffeeList[order.OrderList[i].CoffeeIndex].Name ,order.OrderList[i].NumberOfItems,cup, order.OrderList[i].TotalPrice));
			}
		}
		
		drawLine(DefaultLineLength, false);
		showMessage(String.format(" \t\t\t Total : %s baht",netTotal));
		drawLine(DefaultLineLength, false);

		showMessage(" Back to main menu please press number 9 -> Quit");
		drawLine(DefaultLineLength, true);
		showMessage("Your choice -> ", true);
		
		op.AppMode = OrderProcessor.ProcessMode.Reporting;
	}
    
    //Common utility to clear screen
    public void clearScreen() {
    	System.out.print("\033[H\033[2J");  
    	System.out.flush();
    }
	
    //Common utility to print out message to screen
    public void showMessage(String message) {
		System.out.println(message);	
	}
    
    //Common utility to print out message to screen
    public void showMessage(String message, boolean noNewLine) {
    	if(noNewLine) {
    		System.out.print(message);
    	}
	}
    
	//Common utility to print line out to screen
    public void drawLine(int length, boolean isSingleLine) {
		
		System.out.println(getLine(length,isSingleLine));
		
	}
    //Get string of line character
    public String getLine(int length, boolean isSingleLine) {
    	
		String line = "";
		String lineStyle = "=";
		
		if(isSingleLine) lineStyle = "-";
		
		for(int i=0; i< length; i++)
			line += lineStyle;
		
		return line;
    }
    
	//Common utility to print star out to screen
    public void drawStar(int length) {	
    	
		System.out.println(getStar(length));
		
	}
    //Get string of star character
    public String getStar(int length) {
    	
		String star = "";
		for(int i=0; i< length; i++)
			star += "*";
		
		return star;
    }
	
}
