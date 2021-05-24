package appServices;

import ld.Commit;
import ld.Repositorio;



import java.util.List;
import java.util.ArrayList;

public class CommitService {

    private List<Commit> commits = new ArrayList<>();

    public CommitService() {
        //rellenar lista con BD
    }

    public synchronized void crearCommit() {
        Commit commit = new Commit();
        //añadir variables
        commits.add(commit);
    }

    public synchronized void borrarCommit(int id) {
        for(int i=0;i<commits.size();i++) {
            if(commits.get(i).getIdCommit() == id) {
                commits.remove(commits.get(i));
                break;
            }
        }
    }

    public synchronized Commit devolverCommit(int id) {
        Commit commit = null;

        for(int i=0;i<commits.size();i++) {
            if(commits.get(i).getIdCommit() == id) {
                commit = commits.get(i);
                break;
            }
        }
        return commit;
    }
}
