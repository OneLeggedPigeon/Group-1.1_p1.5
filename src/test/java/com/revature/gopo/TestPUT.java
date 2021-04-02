package com.revature.gopo;

import com.revature.gopo.dao.ReimbursementDao;
import com.revature.gopo.dao.UserDao;
import com.revature.gopo.model.Reimbursement;
import com.revature.gopo.model.User;

public class TestPUT {
    public static void main(String[] args) {
        // create some test objects
        UserDao ud = new UserDao();
        ReimbursementDao rd = new ReimbursementDao();
        User u1 = new User(
                "bob1", "1234", "bob", "mcgee", "email@ur2om.com", 0);
        User u2 = new User(
                "bill1", "1234", "bill", "smithers", "email@ur2om.com", 0);
        User u3 = new User(
                "joe", "5678", "joe", "bob", "email@ur3om.com", 0);
        Reimbursement r1 = new Reimbursement(5.0f, null, null, "reimb1", u1, null);
        Reimbursement r2 = new Reimbursement(5.0f, null, null, "reimb2", u1, null);
        Reimbursement r3 = new Reimbursement(5.0f, null, null, "reimb5", u2, null);
        Reimbursement r4 = new Reimbursement(5.0f, null, null, "reimb4", u2, u1);

        /*===DOA===*/
        u1.setUser_id(9);
        u2.setUser_id(21);
        u3.setUser_id(12);
        // should change bob's username to bob1
        ud.insertOrUpdate(u1);
        // should change bill's username to bill1 and email to ur2om
        ud.insertOrUpdate(u2);
        // should add joe
        ud.insertOrUpdate(u3);

        r3.setId(5);
        r4.setId(4);
        // should add r3
        rd.insertOrUpdate(r3);
        // should change r4's author to 21 and resolver to 9
        rd.insertOrUpdate(r4);
    }
}
