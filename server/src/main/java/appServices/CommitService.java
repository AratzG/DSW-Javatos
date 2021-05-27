package appServices;

import dao.CommitDAO;
import ld.Commit;
import ld.Repositorio;



import java.util.List;
import java.util.ArrayList;

public class CommitService {

    private List<Commit> commits = new ArrayList<>();

    public CommitService() {
        CommitDAO.extraerCommits();
        //rellenar lista con BD
    }

    public synchronized void crearCommit() {
        Commit commit = new Commit();
        //añadir variables
        commits.add(commit);
    }
}
