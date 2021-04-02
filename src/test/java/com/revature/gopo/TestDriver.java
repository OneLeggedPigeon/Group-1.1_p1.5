package com.revature.gopo;

import com.revature.gopo.dao.*;
import com.revature.gopo.model.*;

public class TestDriver {
    public static void main(String[] args) {
        // create some test objects
        UserDao ud = new UserDao();
        ReimbursementDao rd = new ReimbursementDao();
        User u1 = new User(
                "bob", "1234", "bob", "mcgee", "email@ur2om.com", 0);
        User u2 = new User(
                "bill", "1234", "bill", "smithers", "email@urmom.com", 0);
        Reimbursement r1 = new Reimbursement(5.0f, null, null, "reimb1", u1, null);
        Reimbursement r2 = new Reimbursement(5.0f, null, null, "reimb1", u1, null);
        Reimbursement r3 = new Reimbursement(5.0f, null, null, "reimb1", u2, null);
        Reimbursement r4 = new Reimbursement(5.0f, null, null, "reimb1", u2, u1);
        //GET

        //POST
//        ud.insert(u1);
//        ud.insert(u2);
//
//        rd.insert(r1);
//        rd.insert(r2);
//        rd.insert(r3);
//        rd.insert(r4);

        //PUT

        //DELETE
        u1.setUser_id(8);
        ud.delete(u1);
        r3.setId(9);
        rd.delete(r1);
    }
}
