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

        String[][] AccounDetails = new String[0][];
        

        do {

            System.out.print(clear);
            String line = String.format("%s%s%s", color_green, "-".repeat(60), reset);
            String title = String.format("%s%s%s%s", color_Blue, " ".repeat((60 - screen.length()) / 2),
                    screen.toUpperCase(), reset);
            System.out.println(line);
            System.out.println(title);
            System.out.println(line);
            for (int i = 0; i < AccounDetails.length; i++) {
                System.out.println(Arrays.toString(AccounDetails[i]));
            }

            lbl_main: switch (screen) {
                case Dashboard:

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
                case Deposit_Money:
                    do {
                        int accountIndex = accIndex(AccounDetails, "Enter Account number to deposit :");
                        if (accountIndex == -1) {
                            System.out.println("Id is not valid");
                        } else if (accountIndex == -2) {
                            System.out.println("No account on this ID");
                        } else {
                            System.out.println("Account Balence : " + AccounDetails[accountIndex][2]);
                            System.out.print("Enter Deposit Amount : ");
                            int depositAmount = scanner.nextInt();
                            scanner.nextLine();
                            if (depositAmount > 500) {
                                AccounDetails[accountIndex][2] = String
                                        .valueOf(depositAmount + Integer.valueOf(AccounDetails[accountIndex][2]));
                                System.out.println("Deposit was successful");
                            } else {
                                System.out.println("Deposit amount is not enough ");
                            }
                        }
                        System.out.print("Do you want Deposit  Again ? (Y/N) >> ");
                        if (scanner.nextLine().strip().equalsIgnoreCase("N")) {
                            screen = Dashboard;
                            break lbl_main;
                        }
                    } while (true);

                case WithDraw:
                    do {
                        int accountIndex = accIndex(AccounDetails, "Enter Acount number to Withdraw :");
                        if (accountIndex == -1) {
                            System.out.println("Id is not valid");
                        } else if (accountIndex == -2) {
                            System.out.println("No account on this ID");
                        } else {
                            int accountBalence = Integer.valueOf(AccounDetails[accountIndex][2]);
                            System.out.println("Account Balence : " + accountBalence);
                            System.out.print("Enter Withdraw Amount : ");
                            int withdrawAmount = scanner.nextInt();
                            scanner.nextLine();
                            if (withdrawAmount > 100 && accountBalence - withdrawAmount >= 500) {
                                AccounDetails[accountIndex][2] = String
                                        .valueOf(Integer.valueOf(AccounDetails[accountIndex][2]) - withdrawAmount);
                                System.out.println("Withdraw successful");
                            } else {
                                System.out.println("did not succsess withdraw ");
                            }
                        }
                        System.out.print("Do you want withdraw Again ? (Y/N) >> ");
                        if (scanner.nextLine().strip().equalsIgnoreCase("N")) {
                            screen = Dashboard;
                            break lbl_main;
                        }
                    } while (true);
                case Transfer_Money:

                    do {

                        int transferIndex = accIndex(AccounDetails, "Enter Account number of Transfer :");
                        if (transferIndex == -1) {
                            System.out.println("Transfer Id is not valid");
                        } else if (transferIndex == -2) {
                            System.out.println("No Transfer account on this ID");
                        }

                        int transfereeIndex = accIndex(AccounDetails, "Enter Account number of Transferee :");
                        if (transferIndex == -1) {
                            System.out.println("Transferee Id is not valid");
                        } else if (transferIndex == -2) {
                            System.out.println("No Transferee account on this ID");
                        } else {
                            System.out.println("Balance : ".concat(AccounDetails[transferIndex][2]));
                            System.out.println("Balance : ".concat(AccounDetails[transfereeIndex][2]));
                            System.out.print("Enter Transfer Amount : ");
                            double transferAmount = scanner.nextDouble();
                            scanner.nextLine();
                            boolean lessThan100 = transferAmount < 100;
                            boolean lessThan500 = (Double.valueOf(AccounDetails[transferIndex][2])
                                    - (transferAmount + transferAmount * 0.02)) < 500;
                            if (lessThan100 || lessThan500) {
                                System.out.println("Transfer Amount is not suitabal");
                                continue;
                            } else {
                                Double finalTransferLeftAmount = Double.valueOf(AccounDetails[transferIndex][2])
                                        - transferAmount - transferAmount * 0.02;
                                Double finalTransfereeLeftAmount = Double.valueOf(AccounDetails[transfereeIndex][2])
                                        + transferAmount;
                                AccounDetails[transferIndex][2] = String.valueOf(finalTransferLeftAmount);
                                AccounDetails[transfereeIndex][2] = String.valueOf(finalTransfereeLeftAmount);
                            }
                        }
                        System.out.print("Transfer Succsesfull");
                        System.out.println();

                        System.out.print("Do you want Try again? (Y/N) >> ");
                        if (scanner.nextLine().strip().toUpperCase().equals("Y")) {

                            continue;
                        } else {
                            screen = Dashboard;
                            break lbl_main;
                        }
                    } while (true);

                case Acc_Bal:

                    do {
                        int accountIndex = accIndex(AccounDetails, "Enter Account number to check balence :");
                        if (accountIndex == -1) {
                            System.out.println("Id is not valid");
                        } else if (accountIndex == -2) {
                            System.out.println("No account on this ID");
                        } else {
                            System.out.println("Name : ".concat(AccounDetails[accountIndex][1]));
                            System.out.println("Balance : ".concat(AccounDetails[accountIndex][2]));
                            System.out.printf("Amount that can withdraw : %s\n",
                                    (Integer.valueOf(AccounDetails[accountIndex][2]) - 500));
                        }
                        System.out.print("Do you want cheack account balence? (Y/N) >> ");
                        if (scanner.nextLine().strip().toUpperCase().equals("Y")) {

                            continue;
                        } else {
                            screen = Dashboard;
                            break lbl_main;
                        }
                    } while (true);

                case Del_Acc:
                    do {
                        int accountIndex = accIndex(AccounDetails, "Enter Account number to delete :");
                        if (accountIndex == -1) {
                            System.out.println("Id is not valid");
                        } else if (accountIndex == -2) {
                            System.out.println("No account on this ID");
                        } else {
                            String[][] tempArray = new String[AccounDetails.length - 1][3];
                        int tempIndex = 0;
                        for (int i = 0; i < AccounDetails.length; i++) {
                            if (i != accountIndex) {
                                tempArray[tempIndex][0] = AccounDetails[i][0];
                                tempArray[tempIndex][1] = AccounDetails[i][1];
                                tempArray[tempIndex][2] = AccounDetails[i][2];
                                tempIndex++;
                            }
                        }
                        AccounDetails = tempArray;
                        }
                        System.out.println("Account deleted successfully");

                        System.out.print("Do you want to Delete Another Account? (Y/N) >> ");
                        if (scanner.nextLine().strip().equalsIgnoreCase("N")) {
                            screen = Dashboard;
                            break lbl_main;
                        }
                    } while (true);

            }

        } while (true);

    }

    public static boolean isValid(String accNumber, String[][] AccounDetails) {
        if (accNumber.isBlank() || accNumber.length() != 9 || !accNumber.startsWith("SDB-")) {
            return false;
        }
        for (int i = 4; i < accNumber.length(); i++) {
            if (!Character.isDigit(accNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int accIndex(String[][] AccounDetails, String msg) {

        System.out.printf("%s", msg);
        String accNumber = scanner.nextLine().strip();
        if (!isValid(accNumber, AccounDetails)) {
            return -1;
        }
        for (int i = 0; i < AccounDetails.length; i++) {

            if (AccounDetails[i][0].equals(accNumber)) {
                return i;

            }
        }
        return -2;
    }

}
