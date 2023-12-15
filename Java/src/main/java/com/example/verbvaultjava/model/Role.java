package com.example.verbvaultjava.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {
   ADMIN,STUDENT,TUTOR
}
