package PipLab3;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;
import javax.validation.ConstraintViolationException;

@ManagedBean
@SessionScoped
public class AreaChecker implements Serializable {
    private String ssid;
    private Point point;
    private List<Point> pointsList;

    public AreaChecker() {
        this.pointsList = new LinkedList<Point>();
        this.point = new Point();

    }

    public void doCheck(double x) {
        if (x < -4 || x > 4) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error: X out of range1", ""));
            this.point = new Point();
            return;
        }
        point.setX(x);
        doCheck();
    }

    public void doCheck() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fctx.getExternalContext().getSession(true);
        point.setOwnerSSID(session.getId());
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();
        point.setInArea(false);
        if (x >= 0 && y >= 0 && y <= r && x <= r / 2) point.setInArea(true);
        if (x >= 0 && y <= 0 && y >= x - r) point.setInArea(true);
        if (x <= 0 && y <= 0 && x * x + y * y <= r * r / 4) point.setInArea(true);
        pointsList.add(0, point);
        try {
            EntityManagerFactory studentUnit = Persistence.createEntityManagerFactory("data");
            EntityManager entityManager = studentUnit.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(point);
            entityManager.getTransaction().commit();
            entityManager.close();
            studentUnit.close();
        } catch (ConstraintViolationException e) {
            System.out.println("dsa");
        }catch (Throwable ex){

    }
        if (point.getR() != null){
        double rSel = point.getR();
        this.point = new Point();
        point.setR(rSel);
        }else {
            this.point = new Point();
        }
    }


    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public List<Point> getPointsList() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fctx.getExternalContext().getSession(true);
        String ssid=session.getId();
        List<Point> points = new LinkedList<Point>();
        try {
            EntityManagerFactory studentUnit = Persistence.createEntityManagerFactory("data");
            EntityManager entityManager = studentUnit.createEntityManager();
            Query q = entityManager.createQuery("SELECT c FROM Point c");
            points = q.getResultList();
            Iterator<Point> iterator = points.iterator();
            while (iterator.hasNext()) {
                Point point = iterator.next();
                if (point.getOwnerSSID() != ssid) {
                    iterator.remove();
                }
            }

        }catch (Throwable ex){
            fctx.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Server Database isn't available", ""));
            this.point = new Point();
            return points;
        }
        return points;
    }

    public void setPointsList(List<Point> pointsList) {
        this.pointsList = pointsList;
    }
    public int getSize(){
        return getPointsList().size();
    }

    public String getScript(){
        int count = 0;
        String result="<script>";
        List<Point> points=getPointsList();
        result +="var pointsArray = new Array(" + points.size() + ");";
        Iterator<Point> iterator=points.iterator();
        while (iterator.hasNext()) {
            Point point = iterator.next();
            result +=("pointsArray[" + count++ + "] = [" + point.getX() + ","+point.getY()+","+point.getR()+","+point.isInArea()+"];");
        }

        result +="</script>";
        return result;

    }
}
