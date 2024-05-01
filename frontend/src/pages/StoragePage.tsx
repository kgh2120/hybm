import { useState } from 'react';
import Modal from '../components/common/Modal';
import CreateFoodModal from '../components/storagePage/CreateFoodModal';
import styles from '../styles/storagePage/StoragePage.module.css'

function StoragePage() {
  const [isCreateFoodModalOpen, setIsCreateFoodModalOpen] = useState(false);
  const handleOpenCreateFoodModal = () => {
    setIsCreateFoodModalOpen(true);
  };

  const handleCloseCreateFoodModal = () => {
    setIsCreateFoodModalOpen(false);
  };
  return (
    <div className={styles.wrapper}>
      <button onClick={handleOpenCreateFoodModal}>식품등록모달</button>
      {isCreateFoodModalOpen && (
        <Modal title="식품 등록" clickEvent={handleCloseCreateFoodModal}>
          <CreateFoodModal />
        </Modal>
      )}
    </div>
  )
}

export default StoragePage