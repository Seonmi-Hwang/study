package persistence.dao;

import java.util.List;

import service.dto.DeptDTO;

// �а� ������ ó���ϱ� ���� DAO
public interface DeptDAO {
	public List<DeptDTO> getDeptList();		// ��ü �а� ������ ȹ��
	public int insertDept(DeptDTO dept);	// �а������� �߰�
	public int updateDept(DeptDTO dept);	// �а������� ����
	public int deleteDept(int dCode);		// �а������� ����
	public DeptDTO getDeptByName(String dName);		// �а������� �а������� ã��
	public DeptDTO getDeptByCode(String dCode);		// �а������� �а��ڵ�� ã��
}
