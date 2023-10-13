package com.myproject.registerApi.utilities;

import com.myproject.registerApi.model.db.ActivityDb;
import com.myproject.registerApi.model.db.ChildDb;
import com.myproject.registerApi.model.enums.Gender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ChildTestObjects {

    public static final ChildDb childDb = new ChildDb(
            UUID.randomUUID(),
            "firstname",
            "lastname",
            5,
            Gender.MALE,
            "12342143",
            Arrays.asList(
                    ActivityTestObjects.testActivity1,
                    ActivityTestObjects.testActivity2
            )
    );
}
