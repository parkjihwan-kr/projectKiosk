package kiosk;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MainController {
	// 추가 선택 사항
	// 1. 주문 개수 기능 추가
	// 	장바구니에 똑같은 상품이 담기면 주문 화면에서 상품의 개수가 출력되도록
	//  list.add() 시행시 list.get(index)에 접근해서 list.get(index).getName().equal()? 할건지
	//  list를 다 넣고 탐색할건지 
	// 2. 상품 옵션 기능 추가 
	// 	상품에 옵션을 선택 장바구니에 추가 할 수 있게 세분화합니다.
	// 3. 총 판매금액 조회 기능 추가 
	
	// 	구매가 완료될 때마다 총 판매 금액을 누적해줍니다. 
	// 	숨겨진 기능으로 0번 입력 시 총 판매금액을 출력합니다.
	// 4. 총 판매상품 목록 조회 기능 추가 
	// 	구매가 완료될 때마다 판매 상품 목록을 저장해줍니다.
	// 	숨겨진 기능으로 0번 입력시 총 판매 상품 목록을 출력합니다.
	
	private List<Item> icecreamMenu;
    private List<Item> icecreamCakeMenu;
    private List<Item> drinkMenu;
    private List<Item> dessertMenu;
    private List<Item> buyItemOrder;
    // 고객이 주문한 모든 아이템을 저장하는 배열입니다.
    
    private static final int TOTALMONEY = 0;
    private static final int MENU_ICECREAM = 1;
    private static final int MENU_ICECREAM_CAKE = 2;
    private static final int MENU_DESSERT = 3;
    private static final int MENU_DRINK = 4;
    private static final int ORDER_MENU = 5;
    private static final int CANCEL_MENU = 6;
    // switch문을 가독성 좋게 볼 수 있는 상수 지정
    
    private Order order;
    private Scanner sc = new Scanner(System.in);
    /*
    1. 클래스 간의 정합성을 고민하셨는데, 우선 Item, Menu, Order 등 개념적으로 나눈 것을 이해하시고 지금 구현하신 것이라면 지금 코드는 굉장히 잘 정합성을 달성해주신 거 같고요.
	2. Menu와 같이 불변 클래스도 사용 가능한 객체 내 필드 선언은 private final 키워드를 명시해주시면 좋습니다.
	3. 지금은 개인 과제지만 협업을 고려했을 때 커밋은 좀 더 세분화하여 커밋하여야 동료가 파악이기 용이합니다 ! 또한 다른 사람이 코드를 볼 수 있다고 생각하고 코드 작성 후 리팩토링을 많이 해주시는 것도 좋을 거 같습니다.
	4. 이번에 잘해주신 것처럼 클래스를 개념적으로 나누었다면 그 속의 변수명 or 메서드명은 명확하게 작성하는 습관을 가져가시면 좋겠습니다.
	5. steam API는 이번보다는 나중에 for 루프를 돌며 하는 연산을 리팩토링하면서 천천히 적용해보시는 게 좋을 거 같습니다.
	*/
    
    public MainController() {
    	icecreamMenu = new ArrayList<>();
    	icecreamCakeMenu = new ArrayList<>();
    	drinkMenu = new ArrayList<>();
    	dessertMenu = new ArrayList<>();
    	buyItemOrder = new ArrayList<>();
    	icecreamMenu.add(new Item("ice1", 8900, "remeber this"));
    	icecreamMenu.add(new Item("ice2", 12000, "malicious flavor"));
    	icecreamMenu.add(new Item("ice3", 18900, "sour flavor"));

        icecreamCakeMenu.add(new Item("ppice", 11000, "try this"));
        icecreamCakeMenu.add(new Item("qqice", 14000, "yes"));
        icecreamCakeMenu.add(new Item("rrice", 17000, "delicious food"));

        drinkMenu.add(new Item("georgiaCoffee", 4000, "bad"));
        drinkMenu.add(new Item("cocacola", 3000, "good"));

        dessertMenu.add(new Item("dessert1", 4000, "description1"));
        dessertMenu.add(new Item("dessert2", 5000, "description2"));
        order = new Order(buyItemOrder);
        // Order 객체에 고객이 산 모든 아이템을 저장한 리스트를 자원 공유합니다.
    }
    public void show() {
    	System.out.println();
        System.out.println("Baskin Robbins 31에 오신걸 환영합니다.");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.println("		      [Menu]");
        System.out.println("1. Icecream Menu	| Icecream Description");
        System.out.println("2. IcecreamCake Menu	| Icecream Cake Description");
        System.out.println("3. DessertMenu		| Dessert Description");
        System.out.println("4. Drink Menu		| Drink Description");
        System.out.println();
        System.out.println("	     [Order]");
        System.out.println("5. Order	| 장비구니를 확인 후 주문합니다.");
        System.out.println("6. Cancel	| 진행중인 주문을 취소합니다.");
        System.out.println();
        // 첫 출력문
        menuSelect();
    }
    
    private void menuSelect() {
   	 String input = sc.next(); 
   	 try {
	        int choice = Integer.parseInt(input); // 문자열을 정수로 변환
	        switch (choice) {
	        	case TOTALMONEY :
	        		order.showTotalSellList(this);
	        		// 3. 총 판매금액 조회 기능 추가 
	        		// 구매가 완료될 때마다 총 판매 금액을 누적해줍니다. 
	        		// 숨겨진 기능으로 0번 입력 시 총 판매금액을 출력합니다.
	        		// 4. 3번 기능을 들어간 뒤, 사용자 입력으로 0을 누르면 
	        		// 선택사항 4번 기능 추가
	        		break;
	        		// Icecream Menu부터 Drink Menu까지의 출력은
	        		// 메인컨트롤러의 메서드가 담당
	            case MENU_ICECREAM:
	                showMenu(icecreamMenu);
	                break;
	            case MENU_ICECREAM_CAKE:
	                showMenu(icecreamCakeMenu);
	                break;
	            case MENU_DESSERT:
	                showMenu(dessertMenu);
	                break;
	            case MENU_DRINK:
	                showMenu(drinkMenu);
	                break;
	            case ORDER_MENU:   
	                if (buyItemOrder.isEmpty()) {
	                	// 해당 조건문은 주문을 한 내역이 없는데 주문을 누른 경우 
	                	// 즉 리스트에 값이 없는데 입력한 경우입니다.
	                    System.out.println("고객님은 주문하신 내역이 없습니다! 다시 이용해주세요!");
	                    break;
	                }
	                order.showOrder(this);
	                break;
	            case CANCEL_MENU:
	                if (buyItemOrder.isEmpty()) {
	                	// 해당 조건문은 주문을 한 내역이 없는데 취소를 누른 경우 
	                	// 즉 리스트에 값이 없는데 입력한 경우입니다.
	                    System.out.println("고객님은 주문하신 내역이 없습니다! 다시 이용해주세요!");
	                    break;
	                }
	                order.cancelOrder(this);
	                break;
	            default:
	                System.out.println("해당 메뉴판에 없는 번호입니다.");
	                break;
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
	    }
   	
   		/* 출력단
   	 	Baskin Robbins 31에 오신걸 환영합니다.
		아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.
				      		[Menu]
		1. Icecream Menu		| Icecream Description
		2. IcecreamCake Menu	| Icecream Cake Description
		3. DessertMenu			| Dessert Description
		4. Drink Menu			| Drink Description
		*/
    }
    
    private void showMenu(List<Item> menu) {
        System.out.println("=========showMenu=========");
        // 해당 메서드는 menu
        IntStream.range(0, menu.size())
        .mapToObj(i -> {
            Item menuItem = menu.get(i);
            String menuString = menuItem.toString(i + 1); // Menu 클래스의 toString 메서드 호출
            return menuString;
        })
        .forEach(System.out::println);
        /*
        사용자 입력 1:
        =========showMenu=========
		1. ice1    | remeber this
		2. ice2    | malicious flavor
		3. ice3    | sour flavor*/
        int test1 = sc.nextInt();
        if (test1 <= 0 || test1 > menu.size()) {
            System.out.println("잘못된 입력입니다. 다시 시도해주세요");
        } else {
        	MenuItem selectedItem = menu.get(test1 - 1);
            if (selectedItem instanceof Item) {
                buyItemOrder.add((Item) selectedItem); 
            }
            orderItem(selectedItem);
        }
        /* IntStream -> for문 처리
		System.out.println("=========showMenu=========");
		for (int i = 0; i < menu.size(); i++) {
		    Item menuItem = menu.get(i);
		    String menuString = menuItem.toString(i + 1);
		    System.out.println(menuString);
		}
		
		int test1 = sc.nextInt();
		if (test1 <= 0 || test1 > menu.size()) {
		    System.out.println("잘못된 입력입니다. 다시 시도해주세요");
		} else {
		    MenuItem selectedItem = menu.get(test1 - 1);
		    if (selectedItem instanceof Item) {
		        buyItemOrder.add((Item) selectedItem);
		    }
		    orderItem(selectedItem);
		}*/
    }

    private void orderItem(MenuItem item) {
        System.out.println("=========orderItem=========");
        System.out.println(item.getName() + " | W " + item.getPrice() + " | " + item.getDescription());
        System.out.println("위 메뉴의 어떤 옵션으로 추가하겠습니까?");
        int changePrice = (int)(item.getPrice() * 1.5);
        System.out.println("1. Small(W"+ item.getPrice()+") 2. Large(W"+ changePrice+")");
        /*출력단
         =========orderItem=========
		ice2 | W 12000 | malicious flavor
		위 메뉴의 어떤 옵션으로 추가하겠습니까?
		1. Small(W12000) 2. Large(W18000)*/
        int selectOption =sc.nextInt();
        
        switch (selectOption) {
		case 1: 
			//int originalPrice = item.getPrice();
			System.out.println(item.getName()+"(Small) | W" +item.getPrice()+ " | "+item.getDescription());
			// 1. Small(W12000) 2. Large(W18000)
			//item.setPrice(originalPrice);
			break;
		case 2 :
			System.out.println(item.getName()+"(Large) | W" +changePrice+ " | "+item.getDescription());
			item.setPrice(changePrice);
			break;
		default:
			//추후 작성
			break;
		}
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인      2. 취소");
        /* 출력단 
        ice2(Large) | W18000 | malicious flavor
		위 메뉴를 장바구니에 추가하시겠습니까?
		1. 확인      2. 취소 */
    	// 2. 상품 옵션 기능 추가 
    	// 	상품에 옵션을 선택 장바구니에 추가 할 수 있게 세분화합니다.
        
        int addMenu;
        try {
        	addMenu = sc.nextInt();
            // 입력이 정수인 경우에 대한 처리
            switch (addMenu) {
                case 1:
                    // "1"을 입력한 경우
                    System.out.println(item.getName() + "가 장바구니에 추가되었습니다.\n");
                    show();
                    // 다시 메인 메뉴출력인 show()로 되돌아감
                    break;
                case 2:
                    // "2"를 입력한 경우
                    show();
                    break;
                default:
                    // 다른 값을 입력한 경우
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        } catch (InputMismatchException e) {
            // 정수가 아닌 입력이 들어온 경우에 대한 처리
            System.out.println("올바른 숫자를 입력하세요.");
        }
    }
}
