package Designite.SourceModel;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

//TODO check EnumDeclaration, AnnotationTypeDeclaration and nested classes
public class SM_Type extends SM_SourceItem {
	private boolean isAbstract = false;
	private boolean isInterface = false;
	private SM_Package parentPkg;

	private CompilationUnit compilationUnit;
	private TypeDeclaration typeDeclaration;
	private ITypeBinding IType;

	private TypeDeclaration containerClass;
	private boolean nestedClass;
	private Type superclass;
	private List<SM_Type> referencedTypeList = new ArrayList<SM_Type>();

	private List<ImportDeclaration> importList = new ArrayList<>();
	private List<SM_Method> methodList = new ArrayList<SM_Method>();
	private List<SM_Field> fieldList = new ArrayList<SM_Field>();

	public SM_Type(TypeDeclaration typeDeclaration, CompilationUnit compilationUnit, SM_Package pkg) {
		parentPkg = pkg;
		// It has been checked earlier too
		if (typeDeclaration == null || compilationUnit == null)
			throw new NullPointerException();

		name = typeDeclaration.getName().toString();
		this.typeDeclaration = typeDeclaration;
		this.compilationUnit = compilationUnit;
		setIType(typeDeclaration);
		setTypeInfo();
		setAccessModifier(typeDeclaration.getModifiers());
		setSuperClass();
		setImportList(compilationUnit);
	}

	public TypeDeclaration getTypeDeclaration() {
		return typeDeclaration;
	}

	void setIType(TypeDeclaration typeDeclaration) {
		this.IType = typeDeclaration.resolveBinding();
	}

	/*
	 * public ITypeBinding getIType() { return IType; }
	 */

	void setTypeInfo() {
		int modifier = typeDeclaration.getModifiers();
		if (Modifier.isAbstract(modifier))
			isAbstract = true;
		if (typeDeclaration.isInterface())
			isInterface = true;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public void setNestedClass(TypeDeclaration referredClass) {
		nestedClass = true;
		this.containerClass = referredClass;
	}

	public boolean isNestedClass() {
		return nestedClass;
	}

	void setImportList(CompilationUnit unit) {
		ImportVisitor importVisitor = new ImportVisitor();
		unit.accept(importVisitor);
		List<ImportDeclaration> imports = importVisitor.getImports();
		if (imports.size() > 0)
			importList.addAll(imports);
	}

	public List<ImportDeclaration> getImportList() {
		return importList;
	}

	// not implemented yet
	void setSuperClass() {
		superclass = typeDeclaration.getSuperclassType();
	}

	public List<SM_Method> getMethodList() {
		return methodList;
	}

	public List<SM_Field> getFieldList() {
		return fieldList;
	}

	/*
	 * public int countMethods() { return methodList.size(); }
	 */

	/*
	 * public int countFields() { return fieldList.size(); }
	 */

	/*
	 * public int getPublicMethods() { return publicMethods; }
	 * 
	 * public int getPublicFields() { return publicFields; }
	 * 
	 * void computeMetrics(MethodVisitor visitor) { for (int i = 0; i <
	 * countMethods(); i++) { if (accessModifier.equals("PUBLIC"))
	 * publicMethods++; } }
	 * 
	 * void computeMetrics(FieldVisitor visitor) { for (int i = 0; i <
	 * countFields(); i++) { if (accessModifier.equals("PUBLIC"))
	 * publicFields++; } }
	 */

	public SM_Package getParentPkg() {
		return parentPkg;
	}

	/*
	 * public SM_Project getParentProject() { return parentProject; }
	 */

	private void parseMethods() {
		for (SM_Method method : methodList) {
			method.parse();
		}
	}

	private void parseFields() {
		for (SM_Field field : fieldList) {
			field.parse();
		}
	}

	@Override
	public void printDebugLog(PrintWriter writer) {
		print(writer, "\nType: " + name);
		print(writer, "	Parent: " + this.getParentPkg().getName());
		print(writer, "	Access: " + accessModifier);
		print(writer, "	Interface: " + isInterface);
		print(writer, "	Abstract: " + isAbstract);
		print(writer, "	Superclass: " + superclass);
		print(writer, "	Nested class: " + nestedClass);
		if (nestedClass)
			print(writer, "	Referred class: " + containerClass.getName());
		for (SM_Field field : fieldList)
			field.printDebugLog(writer);
		for (SM_Method method : methodList)
			method.printDebugLog(writer);
	}

	@Override
	public void parse() {
		MethodVisitor methodVisitor = new MethodVisitor(typeDeclaration, this);
		typeDeclaration.accept(methodVisitor);
		List<SM_Method> mList = methodVisitor.getMethods();
		if (mList.size() > 0)
			methodList.addAll(mList);
		parseMethods();

		FieldVisitor fieldVisitor = new FieldVisitor(this);
		typeDeclaration.accept(fieldVisitor);
		List<SM_Field> fList = fieldVisitor.getFields();
		if (fList.size() > 0)
			fieldList.addAll(fList);
		parseFields();

	}

	@Override
	public void resolve() {
		// TODO Auto-generated method stub

	}

}
