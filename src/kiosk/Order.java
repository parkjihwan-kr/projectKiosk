package kiosk;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Order {
	private int totalSum = 0;
	// 총 판매 금액을 알 수 있는 totalSum
	private int waitingNumber = 1;
	// 대기 번호 카운팅
    private List<Item> buyItemOrder;
    // MainController와 공유된 리스트 buyItemOrder 
    private List<Item> totalOrderItem = new ArrayList<>();
    // 선택사항 4번을 만족하기 위해 모든 오더 아이템의 list
    private Scanner sc = new Scanner(System.in);
    
    public Order(List<Item> buyItemOrder) {
        this.buyItemOrder = buyItemOrder;
        // 공유하기 위해 생성자 사용
    }
    
    public void showOrder(MainController mainMenu) {
        int totalPrice = buyItemOrder.stream()
                .mapToInt(Item::getPrice)
                .sum();
        
        System.out.println("아래와 같이 주문하시겠습니까?\n");
        System.out.println("============[Orders]============");
        /*
        팀원 피드백 -> maxNameLength, maxPriceLength사용하여 output을 깔끔하게
        */
        
        // List<Item> buyItmeOrder = new ArrayList<>();
        int maxNameLength = buyItemOrder.stream()
                .map(Item::getName)				// list.get(firstIndex~endInex).getName()에 접근
                .mapToInt(String::length)		// list.get(firstIndex~endInex).getName().length();에 접근
                .max()							// list.get(firstIndex~endInex).getName().length().max().orElse(0);
                .orElse(0);						// list.get().getNmae().length().max()에 최댓값 없으면 default로 number -> max().orElse(number)
        
        int maxPriceLength = buyItemOrder.stream()	// 위의 maxNamLength와 비슷한 메커니즘임으로 설명 생략
                .map(Item::getPrice)
                .mapToInt(price -> String.valueOf(price).length())
                .max()
                .orElse(0);
        
        StringBuilder orderSummary = new StringBuilder();
        // StringBuilder에 고객이 주문한 내역을 요약정리하기 위해 만듦.
        
        Map<String, Long> itemGroups = buyItemOrder.stream()
                .collect(Collectors.groupingBy(Item::getDescription, Collectors.counting()));
        // itemGroups는 buyItemOrder의 각각의 객체 즉, Item객체를 각각 받으며
        // 각 객체의 최종연산으로 Collectors.groupingBy()를 쓰고
        // groupingBy는 인자로 item.getDescription(), Collectors.counting()을 받는데
        // Map의 Key부분에 description을 
        // Value부분에 해당 description이 중복되었는지를 카운팅해준다.
        
        itemGroups.forEach((description, count) -> {
        	// 파라미터로 아까 선언한 description과 count를 받고
            Item item = buyItemOrder.stream()
                    .filter(i -> i.getDescription().equals(description))
                    // 객체 Item을 선언 뒤, 아이템의 description과 map의 description과 같은지 비교
                    .findFirst()
                    // 조건에 맞는 첫번째 'Item' 객체를 가져옵니다.
                    // 이후에 발견했더라도 처음에 발견된것만 가져옵니다.
                    .orElse(null);
            		// findFirst()에 부합하지 않는다면 객체는 null을 반환합니다.
            
            if (item != null) {
                String itemSummary = String.format("%-" + (maxNameLength + 1)
                        + "s | $%" + (maxPriceLength + 1)
                        + "s | %2d개 | %s",
                        item.getName(), item.getPrice(), count, description);
                orderSummary.append(itemSummary).append("\n");
                // orderSummary에 이름, 가격, 갯수, 설명을 넣습니다.
            }
        });
        
        /*
         for (Map.Entry<String, Long> entry : itemGroups.entrySet()) {
		    String description = entry.getKey();
		    Long count = entry.getValue();
		    Item item = null;
		    
		    for (Item i : buyItemOrder) {
		        if (i.getDescription().equals(description)) {
		            item = i;
		            break;
		        }
		    }
		    
		    if (item != null) {
		        String itemSummary = String.format("%-" + (maxNameLength + 1)
		                + "s | $%" + (maxPriceLength + 1)
		                + "s | %2d개 | %s",
		                item.getName(), item.getPrice(), count, description);
		        orderSummary.append(itemSummary).append("\n");
		    }
		}

// showTotalSellList() 메서드에서 totalOrderItem.stream().forEach() 부분을 for 루프로 대체
System.out.println("[ 총 판매상품 목록 현황 ]");
System.out.println("현재까지 총 판매된 상품 목록은 아래와 같습니다.");
System.out.println();

for (Item item : totalOrderItem) {
    String itemName = String.format("%-" + (maxNameLength + 1) + "s", item.getName());
    String itemPrice = String.format("W%" + (maxPriceLength + 1) + "d", item.getPrice());
    System.out.println("- " + itemName + " | " + itemPrice);
}
*/

        System.out.println(orderSummary.toString());
        // StringBuilder에 넣은 문자열을 전부 출력

		System.out.println("[Total]");
		System.out.println("W " + totalPrice);
		System.out.println();
        buyItem(mainMenu, totalPrice);
        /*출력단
		[Total]
		W 516000*/
    }
    
    public void buyItem(MainController mainMenu, int totalPrice) { 
        System.out.println("1. 주문	| 2. 메뉴판");
        int select = sc.nextInt();
        switch(select) { 
        case 1: 
        	totalSum += totalPrice;
        	// 3번 선택 요구 사항을 만족하기 위해 totalSum을 고객의 주문내역에서 가져옵니다.
        	System.out.println("주문이 완료되었습니다!"); 
        	System.out.println("대기번호는 ["+waitingNumber+"]번입니다."); 
        	waitingNumber++;
        	for(Item item : buyItemOrder) {
        		// 4번 선택 요구 사항 만족을 위해 모든 고객이 주문한 리스트를 add합니다.
        		totalOrderItem.add(item);
        	}
        	buyItemOrder.clear();
        	System.out.println("3초후 메뉴판으로 돌아갑니다.");
        	try {
                Thread.sleep(3000); // 3초 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        	mainMenu.show();
        	break;
        case 2:
        	mainMenu.show();
        	break;
    	default: 
    		System.out.println("잘못 입력하셨습니다. 다시 이용해주세요");
    		break;
        }
    }
    
    public void cancelOrder(MainController mainMenu) {
    	System.out.println("진행하던 주문을 취소하겠습니까?");
    	System.out.println("1.확인	2.취소");
    	int check = sc.nextInt();
    	switch(check) {
    	case 1: 
    		System.out.println("진행하던 주문이 취소되었습니다.");
    		buyItemOrder.clear();
    		// 리스트buyItemOrder에 있는 모든 요소를 지웁니다.
    		mainMenu.show();
    		break;
    	case 2 : 
    		break;
    	default : 
    		break; 
    	}
    }
    
    // 선택 요구 사항 3
    void showTotalSalesAmount(MainController mainMenu) {
    	// totalSum
    	System.out.println("[ 총 판매금액 현황 ]");
    	System.out.println("현재까지 총 판매된 금액은 [ W "+totalSum+"] 입니다.");
    	System.out.println("0. 총판매금액 보기 1. 돌아가기");
    	int select;
    	try {
    	    select = sc.nextInt(); 
    	    switch (select) {
    	        case 0:
    	        	showTotalSellList(mainMenu);
    	        	// totalSellList
    	            break;
    	        
    	        case 1:
    	            mainMenu.show();
    	            break;
    	        
    	        default:
    	            // 처리할 내용 추가
    	            break;
    	    }
    	} catch (InputMismatchException e) {
    	    System.out.println("유효하지 않은 입력입니다. 정수를 입력하세요.");
    	}
    }
    
    // 선택 요구 사항 4
    void showTotalSellList(MainController mainMenu) {
        int maxNameLength = totalOrderItem.stream()
                .map(Item::getName)				// list.get(firstIndex~endInex).getName()에 접근
                .mapToInt(String::length)		// list.get(firstIndex~endInex).getName().length();에 접근
                .max()							// list.get(firstIndex~endInex).getName().length().max().orElse(0);
                .orElse(0);						// list.get().getNmae().length().max()에 최댓값 없으면 default로 number -> max().orElse(number)
        
        int maxPriceLength = totalOrderItem.stream()
                .map(Item::getPrice)
                .mapToInt(price -> String.valueOf(price).length())
                .max()
                .orElse(0);
        
    	System.out.println("[ 총 판매상품 목록 현황 ]");
    	System.out.println("현재까지 총 판매된 상품 목록은 아래와 같습니다.");
    	System.out.println();
    	totalOrderItem.stream().forEach((item) -> {
            String itemName = String.format("%-" + (maxNameLength + 1) + "s", item.getName());
            String itemPrice = String.format("W%" + (maxPriceLength + 1) + "d", item.getPrice());
            System.out.println("- " + itemName + " | " + itemPrice);
        });
    	// 출력문을 보기 좋게 만드는 작업
    	
    	System.out.println();
        System.out.println("1. 돌아가기");
        int select = sc.nextInt();
        if(select == 1) {
        	mainMenu.show();
        }else {
        	System.out.println("잘못된 입력입니다. 다시 입력해주세요");
        }
    }
}
