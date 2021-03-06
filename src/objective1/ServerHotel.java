package objective1;

import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SuppressWarnings("deprecation")
public class ServerHotel {

	public static void main(String[] args){
		int port = 1099;
		String nomService = "null";
		int numService = 1;
		Registry registry = null;

		if(args.length != 3){
			System.out.println("Arguments de la forme <Port> <Service> <Numero>");
			System.exit(1);
		}else{
			port = Integer.parseInt(args[0]);
			nomService = args[1];
			numService = Integer.parseInt(args[2]);
			//System.out.println("Port " + port + ", Service " + nomService + " et Numero " + numService +  " selectionne");
		}

		if(System.getSecurityManager()==null){
			System.setSecurityManager(new RMISecurityManager());
		}

		try{
			registry=LocateRegistry.createRegistry(port);
		}catch(RemoteException e){
			System.out.println("registry problem : " + e);
		}

		try{

			if(nomService.equals("chaine")){
				String nomGenerique = "Hotels" + numService;
				_Chaine nomChaine = new Chaine("DataStore/"+nomGenerique+".xml");
				registry.bind(nomGenerique, (Remote) nomChaine);
				System.out.println("Enregristrement sur le server reussi");
			}else if(nomService.equals("annuaire")){
				_Annuaire nomAnnuaire = new Annuaire("DataStore/Annuaire.xml");
				registry.bind("Annuaire", (Remote) nomAnnuaire);
				System.out.println("Enregristrement sur le server reussi");
			}else{
				System.out.println("Erreur mauvais nom de service...");
				System.exit(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}