package com.revature.gopo;

import com.revature.gopo.dao.ReimbursementDao;
import com.revature.gopo.dao.UserDao;
import com.revature.gopo.model.Reimbursement;
import com.revature.gopo.model.User;

public class TestPOST {
    public static void main(String[] args) {
        // create some test objects
        UserDao ud = new UserDao();
        ReimbursementDao rd = new ReimbursementDao();
        User u1 = new User(
                "bob3", "1234", "bob", "mcgee", "email@ur2om.com", 0);
        User u2 = new User(
                "bill3", "1234", "bill", "smithers", "email@urmom.com", 0);
        Reimbursement r1 = new Reimbursement(5.0f, null, null, "reimb1", u1, null);
        Reimbursement r2 = new Reimbursement(5.0f, null, null, "reimb2", u1, null);
        Reimbursement r3 = new Reimbursement(5.0f, null, null, "reimb3", u2, null);
        Reimbursement r4 = new Reimbursement(5.0f, null, null, "reimb4", u2, u1);

        /*===DOA===*/
        ud.insert(u1);
        ud.insert(u2);

        rd.insert(r1);
        rd.insert(r2);
        rd.insert(r3);
        rd.insert(r4);
    }
}
