//package prvkolokvium;
//
//class Item {
//    protected String tax_type;
//    protected int price;
//
//    public Item(String tax_type, int price) {
//        this.tax_type = tax_type;
//        this.price = price;
//    }
//
//    public double getTax() {
//        if (tax_type.equals("A")) return price * 0.18;
//        if (tax_type.equals("B")) return price * 0.05;
//        else return 0;
//    }
//
//    public double getTaxReturn() {
//        return getTax() * 0.15;
//    }
//}
//
//class Receipt{
//
//
//
//}
//
//public class MojDDVTest {
//
//    public static void main(String[] args) {
//
//        MojDDV mojDDV = new MojDDV();
//
//        System.out.println("===READING RECORDS FROM INPUT STREAM===");
//        mojDDV.readRecords(System.in);
//
//        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
//        mojDDV.printTaxReturns(System.out);
//
//    }
//}
