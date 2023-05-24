
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;
import com.mifmif.common.regex.Generex;

public class Main {
	public static void main(String[] args) {
		String cart_id_1 = UUID.randomUUID().toString();
		String customer_1 = CustomerID.randomized_ID();
		ShoppingCart cart = new ShoppingCart(cart_id_1, customer_1);
		System.out.println("Created cart successfully");

		Item item_1 = new Item("ABC_DEF_01", 10.00, "A test item");
		Item item_2 = new Item("ABC_DEF_02", 20.00, "Some test item");
		Item item_3 = new Item("ABC_DEF_03", 30.00, "Yet another item");

		// Catalogue.addItem(SKU.randomized_SKU().code(), 19.99, "Some item");

		Catalogue.addItem(item_1);
		Catalogue.addItem(item_2);
		Catalogue.addItem(item_3);

		System.out.println("Created Catalogue successfully");

		cart.addItem(item_1.sku(), new Quantity(1));
		System.out.println();
		cart.addItem(item_2.sku(), new Quantity(2));
		System.out.println();
		cart.addItem(item_3.sku(), new Quantity(3));
		System.out.println();
		System.out.println(cart.totalCost());

		// System.out.println("...\n...\n...");

		cart.removeItem("ABC_DEF_01", new Quantity(1));
		System.out.println(cart.totalCost());
		cart.removeItem("ABC_DEF_02", new Quantity(1));
		System.out.println(cart.totalCost());
		cart.removeItem("ABC_DEF_03", new Quantity(2));
		System.out.println(cart.totalCost());
	}
}

record CustomerID(String id) {
	
	public static String validateID(String id) {
		if (!Pattern.matches("[A-Z]{3}\\d{5}[A-Z]{2}-[A|Q]", id)) {
			try {
				throw new Exception("The Customer ID must be formatted correctly");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	public static String randomized_ID() {
		String regex = "[A-Z]{3}\\d{5}[A-Z]{2}-[AQ]";
		Generex generater = new Generex(regex);
		String randomizedID = generater.random();
		return CustomerID.validateID(randomizedID);
	}
}

record SKU(String code){
	
	public static String validateSKU(String code) {
		if (!Pattern.matches("[A-Z]{3}_[A-Z]{3}_\\d{2}", code)) {
			try {
				throw new Exception("SKU must be formatted correctly");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return code;
	}
	
	public static String randomized_SKU() {
		String regex = "[A-Z]{3}_[A-Z]{3}_\\d{2}";
		Generex generater = new Generex(regex);
		String randomizedID = generater.random();
		return SKU.validateSKU(randomizedID);
	}
}

// TODO: Turn into a record not a class 
class Item {
	private final SKU sku;
	private final double price;
	private final String description;

	public Item(String sku, double price, String description) {
		this.sku = new SKU(SKU.validateSKU(sku));
		this.price = this.validatePrice(price);
		this.description = this.validateDescription(description);
	}

	public SKU sku() {
		return this.sku;
	}

	public double price() {
		return this.price;
	}

	public String description() {
		return this.description;
	}

	public Item copy() {
		return new Item(this.sku.code(), this.price, this.description);
	}

	private String validateDescription(String description) {
		if (description.length() > 20) {
			try {
				throw new Exception("Description must be less than 20 characters");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return description;
	}

	private double validatePrice(double price) {
		if (price <= 0.00) {
			try {
				throw new Exception("Price must be greater than 0.00");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return price;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Item)) {
			return false;
		}
		Item other = (Item) o;
		return (this.sku.equals(other.sku()) && this.price == other.price()
				&& this.description.equals(other.description()));
	}

	@Override
	public int hashCode() {
		return this.sku.hashCode();
	}

}

// TODO: Turn into a record not a class
class Catalogue {
	private static HashMap<SKU, Item> item_SKUs = new HashMap<SKU, Item>();

	// Using primitive parameters
	public static void addItem(String sku, double price, String description) {
		SKU.validateSKU(sku);
		Item item = new Item(sku, price, description);
		Catalogue.addItem(item);
	}

	public static Item getItem(String sku) {
		SKU code = new SKU(SKU.validateSKU(sku));
		return Catalogue.getItem(code);
	}

	public static boolean hasItem(String sku) {
		SKU code = new SKU(SKU.validateSKU(sku));
		return Catalogue.hasItem(code);
	}

	// Using Item & SKU objects rather than Strings
	public static void addItem(Item item) {
		SKU.validateSKU(item.sku().code());
		if (Catalogue.hasItem(item.sku())) {
			try {
				throw new Exception("Another item with that SKU already exists");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		Catalogue.item_SKUs.put(item.sku(), item);
	}

	public static Item getItem(SKU sku) {
		SKU.validateSKU(sku.code());
		if (!Catalogue.hasItem(sku)) {
			try {
				throw new Exception("Item not found");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Catalogue.item_SKUs.get(sku).copy();
	}

	/*
	 * Checks if the given SKU matches with an item in the catalogue, if not then
	 * throw an exception and return false, otherwise return true
	 */
	public static boolean hasItem(SKU sku) {
		SKU.validateSKU(sku.code());
		return Catalogue.item_SKUs.keySet().contains(sku);
	}

	public static boolean check(SKU sku) {
		SKU.validateSKU(sku.code());
		if (!Catalogue.item_SKUs.containsKey(sku)) {
			try {
				throw new Exception("The provided SKU is invalid, no Item in the Catalogue has the given SKU.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}
}

class Quantity {
	private int value;

	public Quantity(int value) {
		this.value = Quantity.validated(value);
	}

	public int value() {
		return this.value;
	}

	public static int validated(int quantity) {
		if (quantity < 0 || quantity >=10) {
			try {
				throw new Exception("Quantity must be greater than 0 and less than 10");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return quantity;
	}

	public static Quantity add(Quantity q1, Quantity q2) {
		return new Quantity(Quantity.validated(q1.value() + q2.value()));
	}

	public static Quantity subtract(Quantity q1, Quantity q2) {
		return new Quantity(Quantity.validated(q1.value() - q2.value()));
	}
}

class ShoppingCart {
	private final UUID cart_id;
	private final CustomerID customer_id;
	private final HashMap<Item, Quantity> items;

	public ShoppingCart(String cart_id, String customer_id) {
		this.cart_id = UUID.fromString(cart_id);
		this.customer_id = new CustomerID(CustomerID.validateID(customer_id));
		this.items = new HashMap<Item, Quantity>();
	}

	public String cartID() {
		return this.cart_id.toString();
	}

	public String customerID() {
		return this.customer_id.toString();
	}

	public HashMap<Item, Quantity> items() {
		HashMap<Item, Quantity> deepCopy = new HashMap<Item, Quantity>();
		for (Item item : this.items.keySet()) {
			deepCopy.put(item.copy(), this.items.get(item));
		}
		return deepCopy;
	}

	/* Overloading addItem() for different parameter types */
	public void addItem(String sku, Quantity amount) {
		SKU code = new SKU(SKU.validateSKU(sku));
		this.addItem(code, amount);
	}

	public void addItem(Item item, Quantity amount) {
		SKU.validateSKU(item.sku().code());
		this.addItem(item.sku(), amount);
	}

	/* This method adds a given amount of an item to the cart */
	public void addItem(SKU sku, Quantity amount) {
		SKU.validateSKU(sku.code());
		// Checks if the given SKU matches with an item in the catalogue, if not then
		// throw an exception and return, else continue
		if (!Catalogue.check(sku)) {
			return;
		}
		Item item = Catalogue.getItem(sku);
		Quantity new_amount;
		// if the item is already in the cart, then increase the amount of that item in
		// the cart by the given amount
		if (this.items.containsKey(item)) {
			new_amount = Quantity.add(this.items.get(item), amount);
			this.items.replace(item, new_amount);
		}
		// if the item is not in the cart, then add the item to the cart with the given
		// amount
		else {
			new_amount = amount;
			this.items.putIfAbsent(item, new_amount);
		}
		// Output success message
		System.out.println(amount.value() + " of Item:" + sku.code() + " has been added to the Cart.");
		System.out.println("The Cart now has " + new_amount.value() + " of Item:" + sku.code() + " in it.");
	}

	/* Overloading removeItem() for different parameter types */
	public void removeItem(String sku, Quantity amount) {
		SKU code = new SKU(SKU.validateSKU(sku));
		this.removeItem(code, amount);
	}

	public void removeItem(Item item, Quantity amount) {
		SKU code = new SKU(SKU.validateSKU(item.sku().code()));
		this.removeItem(code, amount);
	}

	/* This method removes a given amount of an item from the cart */
	public void removeItem(SKU sku, Quantity amount) {
		SKU.validateSKU(sku.code());
		// Checks if the given SKU matches with an item in the catalogue, if not then
		// throw an exception and return, else continue
		if (!Catalogue.check(sku)) {
			return;
		}
		Item item = Catalogue.getItem(SKU.validateSKU(sku.code()));
		// check if item is in the cart, if not then throw an exception and return, else
		// continue
		if (!this.items.containsKey(item)) {
			// Output error message
			System.out.println("Item:" + sku.code() + " cannot be removed since there isn't any in the Cart.");
			try {
				throw new Exception("Item not found in Cart");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		/*
		 * if item is not in the cart then the method will have returned by now
		 * Check if the amount to be removed is less than the amount in the cart,
		 * if so then remove the amount; otherwise, remove all of the item from the cart
		 */
		if (amount.value() < this.items.get(item).value()) {
			Quantity new_amount = Quantity.subtract(this.items.get(item), amount);
			this.items.replace(item, new_amount);
			// Output success message
			System.out.println(amount.value() + " of Item:" + sku.code() + " has been removed from the Cart.");
			System.out.println("The Cart now has " + new_amount.value() + " of Item:" + sku.code() + " in it.");
		} else {
			this.items.remove(item);
			// Output success message
			System.out.println("All of Item:" + sku.code() + " has been removed from the Cart.");
		}
	}

	/* This method calculates the total cost of all the items in the cart */
	public double totalCost() {
		double sumOfPrices = 0.00;
		for (Item item : this.items.keySet()) {
			sumOfPrices += (item.price() * this.items.get(item).value());
		}
		return sumOfPrices;
	}

}


// class CustomerID {
// 	private final String id;

// 	public CustomerID(String id) {
// 		this.id = CustomerID.validateID(id);
// 	}

// 	public static String validated(String id) {
// 		if (!Pattern.matches("[A-Z]{3}\\d{5}[A-Z]{2}-[A|Q]", id)) {
// 			try {
// 				throw new Exception("The Customer ID must be formatted correctly");
// 			} catch (Exception e) {
// 				e.printStackTrace();
// 			}
// 		}
// 		return id;
// 	}

// 	public static CustomerID randomized_ID() {
// 		;
// 		String regex = "[A-Z]{3}\\d{5}[A-Z]{2}-[AQ]";
// 		Generex generater = new Generex(regex);
// 		String randomizedID = generater.random();
// 		return new CustomerID(CustomerID.validateID(randomizedID));
// 	}

// 	public String toString() {
// 		return this.id;
// 	}
// }

// class SKU {
// 	private final String skuCode;

// 	public SKU(String code) {
// 		this.skuCode = SKU.validateSKU(code);
// 	}

// 	public static String validated(String code) {
// 		if (!Pattern.matches("[A-Z]{3}_[A-Z]{3}_\\d{2}", code)) {
// 			try {
// 				throw new Exception("SKU must be formatted correctly");
// 			} catch (Exception e) {
// 				e.printStackTrace();
// 			}
// 		}
// 		return code;
// 	}

// 	public static SKU randomized_SKU() {
// 		;
// 		String regex = "[A-Z]{3}_[A-Z]{3}_\\d{2}";
// 		Generex generater = new Generex(regex);
// 		String randomizedID = generater.random();
// 		return new SKU(SKU.validateSKU(randomizedID));
// 	}

// 	public String code() {
// 		return this.skuCode;
// 	}
	
// 	public String toString() {
// 		return this.skuCode;
// 	}

// 	@Override
// 	public boolean equals(Object o) {
// 		if (o == this) {
// 			return true;
// 		}
// 		if (!(o instanceof SKU || o instanceof String)) {
// 			return false;
// 		}
// 		if (o instanceof String) {
// 			return this.skuCode.equals(SKU.validateSKU(o.toString()));
// 		}
// 		SKU sku = (SKU) o;
//         return this.skuCode.compareTo(sku.code()) == 0;
// 	}

// 	@Override
//     public int hashCode(){
//         return this.skuCode.hashCode();
//     }
// }
