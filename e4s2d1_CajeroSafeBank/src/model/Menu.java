package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private boolean home;
    private String dni;
    private Scanner s;
    private ArrayList<Customer> cl;
    private boolean cookie;
    private int clave;

    public Menu() {
        this.home = true;
        this.dni = "";
        s = new Scanner(System.in);
        this.cl = cl;
        this.clave = -1;
    }

    public void StartMenu(ArrayList<Customer> cl) {
        home = true;
        cookie = false;
        while (home) {
            while (!cookie) {
                System.out.println("         SAFEBANK         ");
                System.out.println();
                System.out.println(" INGRESE RUT SIN DV: ");
                this.dni = s.nextLine();
                System.out.println(" INGRESE CLAVE: ");
                this.clave = s.nextInt();
                for (Customer c : cl) {
                    if (c.getPassword() == clave && c.getDni().equals(dni.toString())) {
                        home = false;
                        cookie = true;
                        secondScreen(c);
                    }
                }
                if (!cookie) {
                    System.out.println("NOMBRE O CONTRASEÑA INCORRECTA.");
                    dni = "";
                    clave = -1;
                }
            }
        }
    }

    public void secondScreen(Customer c) {
        boolean secondScreen = true;
        int opcion;
        while (cookie) {
            System.out.printf("Bienvenido %s (%d Años)", c.getName(), c.calcularEdad());
            System.out.println("Qué deseas hacer? ");
            System.out.println();
            System.out.println("1. MENU CTA CTE.");
            System.out.println("2. MENU TARJETA CRÉDITO.");
            System.out.println("3. VER DATOS DE MI EJECUTIVO.");
            System.out.println("4. SALIR.");
            opcion = s.nextInt();
            switch (opcion) {
                case 1:
                    checkingAccountMenu(c);
                    break;
                case 2:
                    creditCardAccount(c);
                    break;
                case 3:
                    accountExecutiveMenu(c);
                    break;
                case 4:
                    System.out.println("Cerrando Sesión");
                    System.out.println("Hasta luego "+c.getName());
                    cookie = false;
                    break;
                default:
                    break;
            }
        }
    }

    public void checkingAccountMenu(Customer c) {
        boolean cam = true;
        while (cam) {
            System.out.println("CUENTA CORRIENTE: " + c.getCa().getAccountNumber());
            System.out.println("SALDO ACTUAL: " + c.getCa().getAvailable());
            if (c.getCa().isMonthlyPrice()) {
                System.out.println("LA DEUDA DE SU CUENTA ES DE: 15000");
            } else System.out.println("No tienes deuda.");
            System.out.println("Qué desea hacer?");
            System.out.println("1. Volver al menu.");
            System.out.println("2. Retirar dinero.");
            if (c.getCa().isMonthlyPrice()) {
                System.out.println("3. Pagar mi Deuda.");
            } else System.out.println();
            switch (s.nextInt()) {
                case 1:
                    cam = false;
                    secondScreen(c);
                    break;
                case 2:
                    System.out.println("Cuánto desea retirar? (0 para cancelar)");
                    int cash = s.nextInt();
                    System.out.println("DINERO RETURADO, VOLVIENDO AL MENU DE CTA CTE");
                    c.getCa().setAvailable(c.getCa().getAvailable() - cash);
                    break;
                case 3:
                    c.getCa().setMonthlyPrice(false);
                    System.out.println("Su deuda ha sido pagada.");
                    break;
                default:
                    break;
            }
        }
    }

    public void creditCardAccount(Customer c) {
        System.out.println("Tarjeta de credito"+ c.getCc().getAccountNumber());
        System.out.println("Saldo actual: "+(c.getCc().getCredit()-c.getCc().getUtilizado()));
    }

    public void accountExecutiveMenu(Customer c) {
        System.out.println("Datos de tu ejecutivo:");

        System.out.printf("Nombre: %s, Teléfono: %s, Dirección: %s", c.getCa().getAccountExec().getName(), c.getCa().getAccountExec().getPhone(), c.getCa().getAccountExec().getDireccion());
        System.out.println("... volviendo al menú principal");
        secondScreen(c);
    }
}
