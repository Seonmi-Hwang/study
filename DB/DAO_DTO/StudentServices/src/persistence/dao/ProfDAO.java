package persistence.dao;

import java.util.List;

import service.dto.ProfDTO;



// ���������� ����ϴ� DAO
public interface ProfDAO {
	public List<ProfDTO> getProfList();				// ��ü ���� ������ ȹ�� 
	public int insertProf(ProfDTO dept);			// �������� �߰�
	public int updateProf(ProfDTO dept);			// �������� ����
	public int deleteProf(int pCode);				// �������� ����
	public ProfDTO getProfByName(String name);		// ���������� ������������ ã��
	public ProfDTO getProfByCode(String pCode);		// ���������� �����ڵ�� ã��
}
