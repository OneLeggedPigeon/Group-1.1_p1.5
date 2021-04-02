package com.revature.gopo;

import com.revature.gopo.dao.*;
import com.revature.gopo.model.*;

public class TestGET {
    public static void main(String[] args) {
        // create some test objects
        UserDao ud = new UserDao();
        ReimbursementDao rd = new ReimbursementDao();
        User u1 = new User(
                "bob", "1234", "bob", "mcgee", "email@ur2om.com", 0);
        User u2 = new User(
                "bill", "1234", "bill", "smithers", "email@urmom.com", 0);
        Reimbursement r1 = new Reimbursement(5.0f, null, null, "reimb1", u1, null);
        Reimbursement r2 = new Reimbursement(5.0f, null, null, "reimb2", u1, null);
        Reimbursement r3 = new Reimbursement(5.0f, null, null, "reimb3", u2, null);
        Reimbursement r4 = new Reimbursement(5.0f, null, null, "reimb4", u2, u1);

        /*===DOA===*/
        System.out.println(
                ud.getById(2).getUser_id() + System.lineSeparator() +
                ud.getByUserId(2).get(0).getUser_id() + System.lineSeparator() +
                ud.getByUsername("bill").getUser_id() + System.lineSeparator() +
                rd.getById(6).getId() + System.lineSeparator() +
                rd.getByUserId(2).get(0).getId()
        );
    }
}
