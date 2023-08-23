import java.util.Arrays;
import java.util.Scanner;

class SmartBanking {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        final String clear = "\033[H\033[2J";
        final String color_Blue = "\033[1;34m", color_green = "\033[0;32m", Purple = "\033[0;35m", reset = "\033[0m",
                Yellow = "\033[0;33m", GREEN_BACKGROUND = "\033[42m";

        final String Dashboard = "Smart Banking System";
        final String Open_New_Acc = "Open New Account";
        final String Deposit_Money = "Deposit Money";
        final String WithDraw = "Withdraw Money";
        final String Transfer_Money = "Transfer Money";
        final String Acc_Bal = "Check Account Balance";
        final String Del_Acc = "Delete Account";

        String screen = Dashboard;

        // String[][] AccounDetails = new String[0][];
        String[][] AccounDetails = {{"SDB-00001","pravinda","7000"},{"SDB-00002","pravi","7000"},{"SDB-00003","prav","7000"}}; //test case
        
        main_loop: do {

            System.out.print(clear);
            String line = String.format("%s%s%s", color_green, "-".repeat(60), reset);
            String title = String.format("%s%s%s%s", color_Blue, " ".repeat((60 - screen.length()) / 2),screen.toUpperCase(), reset);
            System.out.println(line);
            System.out.println(title);
            System.out.println(line);


            lbl_main: switch (screen) {
                case Dashboard:

                    for (int index = 0; index < AccounDetails.length; index++) { 
                        System.out.println(Arrays.toString(AccounDetails[index]));
                    }
                    System.out.println(isValid("SDB-00003", AccounDetails)); //testing

                    System.out.println("[1]. Open New Account");
                    System.out.println("[2]. Deposit Money");
                    System.out.println("[3]. Withdraw Money");
                    System.out.println("[4]. Transfer Money");
                    System.out.println("[5]. Check Account Balance");
                    System.out.println("[6]. Delete Account");
                    System.out.println("[7]. Exit");
                    System.out.print("Enter an Option to Continue >> ");
                    int option = scanner.nextInt();
                    scanner.nextLine();

                    switch (option) {
                        case 1:
                            screen = Open_New_Acc;
                            break;
                        case 2:
                            screen = Deposit_Money;
                            break;
                        case 3:
                            screen = WithDraw;
                            break;
                        case 4:
                            screen = Transfer_Money;
                            break;
                        case 5:
                            screen = Acc_Bal;
                            break;
                        case 6:
                            screen = Del_Acc;
                            break;
                        case 7:
                            System.exit(0);
                            break;
                    }
                    break;

                case Open_New_Acc:
                    boolean validName = false;
                    int x = 1, initDepo;
                    String id, accName;
                    loop_name: while (true) {
                        id = String.format("SDB-%05d", x);
                        System.out.printf("New Account number => %s%s%s\n", Purple, id, reset);
                        System.out.print("Enter name: ");
                        accName = scanner.nextLine().strip();
                        // name validation
                        if (accName.isBlank()) {
                            System.out.println("Name cannot be Empty. Try Again");
                            continue;
                        } else {
                            for (int i = 0; i < accName.length(); i++) {
                                if (!(Character.isLetter(accName.toLowerCase().charAt(i))
                                        || accName.charAt(i) == ' ')) {
                                            
                                    System.out.print("Invalid Name. Do you want Enter a valid name? (Y/N) >> ");
                                    if (scanner.nextLine().strip().toUpperCase().equals("Y"))
                                        continue loop_name;
                                    screen = Dashboard;
                                    break loop_name;
                                }
                            }
                        }

                    

                        do {
                            System.out.print("Initial Deposit: ");
                            initDepo = scanner.nextInt();
                            scanner.nextLine();
                            if (initDepo < 5000) {
                                System.out.print(
                                        "Insufficient Deposit. Do you want to Deposit a sufficient amount? (Y/N): ");
                                if (scanner.nextLine().strip().toUpperCase().equals("Y"))
                                    continue;
                                else {
                                    screen = Dashboard;
                                    break loop_name;
                                }
                            } else {
                                System.out.printf("Account number %s%s%s of %s\033[1;30m%s%s has been created\n",
                                        Yellow, id, reset, GREEN_BACKGROUND, accName.toUpperCase(), reset);
                                break;
                            }
                        } while (true);


                        String[][] tempDetails = new String[AccounDetails.length + 1][3];
                        for (int i = 0; i < AccounDetails.length; i++) {
                            tempDetails[i] = AccounDetails[i];
                        }
                        tempDetails[tempDetails.length - 1][0] = id;
                        tempDetails[tempDetails.length - 1][1] = accName;
                        tempDetails[tempDetails.length - 1][2] = String.valueOf(initDepo);

                        AccounDetails = tempDetails;

                        System.out.print("Do you want to Open another Account? (Y/N) >> ");
                        if (scanner.nextLine().strip().toUpperCase().equals("Y")) {
                            x++;
                            continue;
                        } else {
                            screen = Dashboard;
                            break lbl_main;
                        }
                    }

            }
        } while (true);
        
    }

    
    public static boolean isValid(String accNumber, String[][] accList) {
        if (accNumber.isBlank() || accNumber.length() != 9 || !accNumber.startsWith("SDB-")) {
            return false;
        }
    
        for (int i = 4; i < accNumber.length(); i++) {
            if (!Character.isDigit(accNumber.charAt(i))) {
                return false;
            }
        }
    
        for (int k = 0; k < accList.length; k++) {
            if (accNumber.equals(accList[k][0])) {
                return true;
            }
        }
    
        return false;
    }
    

   
}

