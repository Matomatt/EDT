package AbstractListModels;

import java.util.List;

import javax.swing.AbstractListModel;

import Utilisateurs.Utilisateur;

@SuppressWarnings("rawtypes")
public class UtilisateurListModel extends AbstractListModel
{
	private static final long serialVersionUID = -8532732162793958629L;
	private final List<Utilisateur> list;

    public UtilisateurListModel(List<Utilisateur> list) {
        this.list = list;
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Object getElementAt(int index) {
        return list.get(index);
    }
}
