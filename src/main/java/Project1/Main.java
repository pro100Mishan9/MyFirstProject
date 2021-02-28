package Project1;

import Project1.Entitys.BuildingsTools;
import Project1.Enums.ToolsStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scannerString = new Scanner(System.in);
    private static final Scanner scannerInt = new Scanner(System.in);
    private static final SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    private static final Session session = sessionFactory.openSession();
    private static final BuildingsTools buildingsTools = new BuildingsTools();


    public static void main(String[] args) {
        System.out.println("Choose operation:" +
                "\n1.Add element" +
                "\n2.Delete element" +
                "\n3.Update element" +
                "\n4.Show all tools");
        int operation = scannerInt.nextInt();
        switch (operation) {
            case 1:
                addElement();
                break;
            case 2:
                deleteElement();
                break;
            case 3:
                updateElement();
                break;
            case 4:
                showAllTools();
                break;
            default:
                System.out.println("Unknown operation");
        }
    }


    private static void addElement() {
        while (true) {
            setName();
            setAttention();
            setStatus();
            setDateAdded();

            saveElement();
            System.out.println("Building tool was added");

            System.out.println("Do you want to continue? Y/N");
            String continueOrNot = scannerString.nextLine().toUpperCase();
            if (continueOrNot.equals("N")) {
                HibernateUtils.shutDownSession();
                System.out.println("Session was closed");
                break;
            }
        }
    }

    private static void deleteElement() {
        while (true) {
            Transaction tx = session.beginTransaction();
            System.out.println("Enter id of building tools to delete:");
            showAllTools();
            int tools = scannerInt.nextInt();
            Query query = session.createQuery("delete from BuildingsTools where id=" + tools);
            query.executeUpdate();
            tx.commit();
            session.close();
            System.out.println("Building tool was deleted");

            System.out.println("Do you want to continue? Y/N");
            String continueOrNot = scannerString.nextLine().toUpperCase();
            if (continueOrNot.equals("N")) {
                HibernateUtils.shutDownSession();
                System.out.println("Session was closed");
                break;
            }
        }
    }

    private static void updateElement() {
        while (true) {
            Transaction tx = session.beginTransaction();
            System.out.println("Enter id of building tools to update:");
            showAllTools();
            int tools = scannerInt.nextInt();
            System.out.println("Enter a new name");
            String newName = scannerString.nextLine();
            System.out.println("Enter a new attention");
            String newAttention = scannerString.nextLine();

            System.out.println("Choose status:" +
                    "\n1.Available" +
                    "\n2.Not Available");
            int status = scannerInt.nextInt();
            String newStatus = "";
            if (status == 1) {
                newStatus = newStatus + "" + ToolsStatus.AVAILABLE.getStatus();
            } else if (status == 2) {
                newStatus = newStatus + "" + ToolsStatus.NOT_AVAILABLE.getStatus();
            } else {
                System.out.println("Unknown status");
            }

            String hql = "UPDATE BuildingsTools set name = :name, attention = :attention, " +
                    "status = :status, dateOfChange = :dateOfChange " +
                    "WHERE id = :id";


            Query query = session.createQuery(hql);
            query.setParameter("name", newName);
            query.setParameter("attention", newAttention);
            query.setParameter("status", newStatus);
            query.setParameter("dateOfChange", LocalDateTime.now());
            query.setParameter("id", tools);
            query.executeUpdate();
            tx.commit();
            session.close();
            System.out.println("Building tool was update");

            System.out.println("Do you want to continue? Y/N");
            String continueOrNot = scannerString.nextLine().toUpperCase();
            if (continueOrNot.equals("N")) {
                HibernateUtils.shutDownSession();
                System.out.println("Session was closed");
                break;
            }
        }
    }

    private static void showAllTools() {
        Query from_BuildingsTools = session.createQuery("from BuildingsTools");
        List<BuildingsTools> resultList = from_BuildingsTools.getResultList();
        System.out.println("List of tools:");
        resultList.forEach(tool -> System.out.println(tool.getId() + "." + tool.getName() +
                ": status - " + tool.getStatus() +
                ", attention - " + tool.getAttention()));
    }

    private static void saveElement() {

        session.beginTransaction();
        session.save(Main.buildingsTools);
        session.getTransaction().commit();

        session.close();
    }

    private static void setName() {
        System.out.println("Enter a name");
        String name = scannerString.nextLine();
        buildingsTools.setName(name);
    }

    private static void setAttention() {
        System.out.println("Enter attention");
        String attention = scannerString.nextLine();
        buildingsTools.setAttention(attention);
    }

    private static void setStatus() {
        System.out.println("Choose status:" +
                "\n1.Available" +
                "\n2.Not Available");
        int status = scannerInt.nextInt();
        if (status == 1) {
            buildingsTools.setStatus(ToolsStatus.AVAILABLE.getStatus());
        } else if (status == 2) {
            buildingsTools.setStatus(ToolsStatus.NOT_AVAILABLE.getStatus());
        } else {
            System.out.println("Unknown status");
        }
    }

    private static void setDateAdded() {
        buildingsTools.setDateAdded(LocalDateTime.now());
    }
}
