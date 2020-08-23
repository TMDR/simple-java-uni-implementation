/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.simplejavaassignments;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author rolan
 */
public class University {

    private Map<String /*Departments*/, Map<String/*Groups*/, Map<String/*Students*/, Map<String/*subject name*/, Integer/*Grade*/>>>> university = new HashMap<> /*Departments*/ /*Groups*/ /*Students*/ /*subject name*/ /*Grade*/();

    public double averageOfAStudents(String StudentName) throws scoreException, NoSubjectsForAStudentException, NoGroupsAtADepartmentException, NoDepartmentsAtTheUniversityException {
        Set<String> Students;
        Set<String> Departments = university.keySet();
        double average=0;//average of grades
        if (Departments.isEmpty()) {
            throw new NoDepartmentsAtTheUniversityException("no departments in the university");
        }
        Iterator<String> itDep = Departments.iterator();
        Iterator<String> itGrp;
        Set<String> Groups;
        while (itDep.hasNext()) {
            String Dep = itDep.next();
            Groups = university.get(Dep)/*here is Map<String,Map<String,Map<String,Integer>>>*/.keySet()/*getting the first string 
                    as it is the key we are getting all groups from all reported departments in the university*/;
            itGrp = Groups.iterator();
            if (Groups.isEmpty()) {
                throw new NoGroupsAtADepartmentException("no groups in the department " + Dep);
            }
            while (itGrp.hasNext()) {
                String Grp = itGrp.next();
                if (university.get(Dep).get(Grp).containsKey(StudentName)) {
                    double sum = 0;//sum of grades
                    double nbSubjects = university.get(Dep).get(Grp).get(StudentName).size();//number of subjects recorded by that student
                    if (nbSubjects == 0) {
                        throw new NoSubjectsForAStudentException("no subjects for " + StudentName);
                    }
                    Set<String> Subjects = university.get(Dep).get(Grp).get(StudentName).keySet();
                    Iterator<String> itSub = Subjects.iterator();
                    while (itSub.hasNext()) {
                        sum += university.get(Dep).get(Grp).get(StudentName).get(itSub.next());
                    }
                    average = sum / nbSubjects;
                    if (average > 10) {
                        throw new scoreException("value superior to 10");
                    }
                    if (average < 0) {
                        throw new scoreException("negative value");
                    }
                }
            }
        }
        return average;
    }

    public double averageSpecificSubject(String Department, String Group, String Subject) throws scoreException, NoStudentsInTheGroupException, NoGroupsAtADepartmentException, NoDepartmentsAtTheUniversityException {//public averageSpecificSubject(){Calculate an average score for a specific subject in a specific group and specific department
        Set<String> Students = university.get(Department).get(Group).keySet();
        if (university.keySet().isEmpty()) {
            throw new NoDepartmentsAtTheUniversityException("no departments in the university");
        }
        double average;
        double sum = 0;//sum of grades of students having the subject
        double nbStudents = 0;//number of students having the subject
        Iterator<String> it = Students.iterator();
        while (it.hasNext()) {
            String Student = it.next();
            if (university.get(Department).isEmpty()) {
                throw new NoGroupsAtADepartmentException("no groups in the department " + Department);
            }
            if (university.get(Department).get(Group).get(it.next()).containsKey(Subject)) {//if that student has the subject
                sum += university.get(Department).get(Group).get(it.next()).get(Subject);
                nbStudents++;
            }
        }
        if (nbStudents == 0) {
            throw new NoStudentsInTheGroupException("no students in the group " + Group);
        }
        average = sum / nbStudents;
        if (average > 10) {
            throw new scoreException("value superior to 10");
        }
        if (average < 0) {
            throw new scoreException("negative value");
        }
        return average;
    }

    public double universityWideAverageOnSubject(String Subject) throws scoreException, NoStudentsInTheGroupException, NoGroupsAtADepartmentException, NoDepartmentsAtTheUniversityException {//Calculate the university-wide average grade for a specific subject
        double average;
        double sum = 0;//sum of grades of students having the subject
        double nbStudents = 0;//number of students having the subject
        Set<String> Dep = university.keySet();
        if (Dep.isEmpty()) {
            throw new NoDepartmentsAtTheUniversityException("no departments in the university");
        }
        Iterator<String> itDep = Dep.iterator();
        while (itDep.hasNext()) {
            String Department = itDep.next();
            Set<String> Groupe = university.get(Department).keySet();
            Iterator<String> itGrp = Groupe.iterator();
            if (Groupe.isEmpty()) {
                throw new NoGroupsAtADepartmentException("no groups in the department" + Department);
            }
            while (itGrp.hasNext()) {
                String Group = itGrp.next();
                Set<String> Students = university.get(Department).get(Group).keySet();
                Iterator<String> it = Students.iterator();
                if (Students.isEmpty()) {
                    throw new NoStudentsInTheGroupException("no students in the group " + Group);
                }
                while (it.hasNext()) {
                    String Student = it.next();
                    if (university.get(Department).get(Group).get(it.next()).containsKey(Subject)) {//if that student has the subject
                        sum += university.get(Department).get(Group).get(it.next()).get(Subject);
                        nbStudents++;
                    }
                }
            }
        }
        average = sum / nbStudents;
        if (average > 10) {
            throw new scoreException("value superior to 10");
        }
        if (average < 0) {
            throw new scoreException("negative value");
        }
        return average;
    }

}
