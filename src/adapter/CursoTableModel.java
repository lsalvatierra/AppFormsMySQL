package adapter;

import model.Curso;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CursoTableModel extends AbstractTableModel {

    private final String[] COLUMNAS = {"CÓDIGO", "NOMBRE", "CRÉDITO"};
    private List<Curso> cursos;

    public CursoTableModel(List<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public int getRowCount() {
        return cursos.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNAS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch(columnIndex){
            case 0 -> cursos.get(rowIndex).getIdcurso();
            case 1 -> cursos.get(rowIndex).getNomcurso();
            case 2 -> cursos.get(rowIndex).getCredito();
            default -> "-";
        };
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNAS[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(getValueAt(0,columnIndex) != null){
            return getValueAt(0, columnIndex).getClass();
        }else{
            return Object.class;
        }
    }
}
